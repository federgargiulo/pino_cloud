package it.pliot.equipment.service.ext;

import it.pliot.equipment.Const;
import it.pliot.equipment.GlobalConfig;
import it.pliot.equipment.Mode;
import it.pliot.equipment.io.UserGrpTO;
import it.pliot.equipment.io.UserTO;
import it.pliot.equipment.model.User;
import it.pliot.equipment.model.UserGrp;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.repository.UserRepository;
import it.pliot.equipment.security.JwtUser;
import it.pliot.equipment.security.UserContext;
import it.pliot.equipment.service.business.TenantServices;
import it.pliot.equipment.service.business.UserGrpServices;
import it.pliot.equipment.service.business.UserServices;
import it.pliot.equipment.service.dbms.BaseServiceImpl;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.UserGrpUtils;
import it.pliot.equipment.service.dbms.util.UserUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class UserServiceImpl extends BaseServiceImpl<UserTO,User,String> implements UserServices {



    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    GlobalConfig config;

    @Autowired
    KeycloakUserExtension keycloak;

    @Autowired
    private UserGrpServices userGrpServices;

    @Autowired
    private TenantServices tenatServices;

    @Autowired
    private UserRepository userRepo;

    @Override
    public PliotJpaRepository<User, String> getRepo() {
        return userRepo;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return UserUtils.instance();
    }



    @Override
    public UserTO create(UserTO io) {

        String [] groupsid = new String[]{ Const.GROUP_PREFIX + io.getTenant() , Const.USER_TENANT_GRP };
        if (Mode.SERVER == config.getMode() )
           io = keycloak.createUser( io , groupsid );

        return super.create(io);
    }

    @Override
    public List<UserTO> findUsersByTenant(String tenant  ){

        JwtUser u = UserContext.currentUser();
        log.info( " user {} " , u );

        User probe = new User();
        probe.setTenant( tenant );
        Example<User> ex = Example.of(probe);
        return findAllAsTo( ex );
    }

    @Override
    public UserTO save(UserTO io) {
        Optional<User> opUser = getRepo().findById(io.getIdpId());
        if(opUser.isEmpty())
             throw new RuntimeException("User not present:"+io.getIdpId());
        User user = opUser.get();
        io.getUsrGrp()


        if (Mode.SERVER == config.getMode()) {
            // Aggiorna lo user settando i nuovi gruppi su Keycloak
            keycloak.updateUser(io);
        }
        // Aggiorna l'utente nel database
        return save(io);
    }

    private HashMap<String, OperationType> checKGroupToAddOrRemove(List<UserGrpTO>  userGroupTos, List<UserGrp> user){

        HashMap<String, OperationType> mappa = new HashMap<String, OperationType>();
       if(user.isEmpty()) {
           userGroupTos.forEach(x->mappa.put(x.getGrpName(), OperationType.ADD));
       }
       for(int i =0; i< user.size(); i++){
           UserGrp u = user.get(i);
           if (!isContained(userGroupTos, u.getGrpName())){
               mappa.put(u.getGrpName(), OperationType.DELETE);
           }

       }

       return mappa;
    }


    private boolean isContained (List<UserGrpTO>  userGroupTos, String groupName){

        for(int i =0; i< userGroupTos.size(); i++){
            UserGrpTO u = userGroupTos.get(i);
            if(groupName.equals(u.getGrpName())) return true;
        }
        return false;
    }



}
