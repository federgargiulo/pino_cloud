package it.pliot.equipment.service.ext;

import it.pliot.equipment.GlobalConfig;
import it.pliot.equipment.Mode;
import it.pliot.equipment.io.UserTO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.model.User;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.repository.UserRepository;
import it.pliot.equipment.service.business.UserServices;
import it.pliot.equipment.service.dbms.BaseServiceImpl;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.UserUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
public class UserServiceImpl extends BaseServiceImpl<UserTO,User,String> implements UserServices {

    @Autowired
    GlobalConfig config;

    @Autowired
    KeycloakUserExtension keycloak;


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
        if (Mode.SERVER == config.getMode() )
           io = keycloak.createUser( io );
        return super.create(io);
    }

    @Override
    public List<UserTO> findUsersByTenant(String tenant  ){
        User probe = new User();
        probe.setTenant( tenant );
        Example<User> ex = Example.of(probe);
        return findAllAsTo( ex );
    }


}
