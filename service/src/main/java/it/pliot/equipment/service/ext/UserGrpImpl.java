package it.pliot.equipment.service.ext;

import it.pliot.equipment.io.UserGrpTO;
import it.pliot.equipment.model.UserGrp;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.repository.RoleRepository;
import it.pliot.equipment.service.business.UserGrpServices;
import it.pliot.equipment.service.dbms.BaseServiceImpl;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.UserGrpUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
@Transactional
public class UserGrpImpl extends BaseServiceImpl<UserGrpTO, UserGrp, String> implements UserGrpServices {

    private static final Logger log = LoggerFactory.getLogger(UserGrpImpl.class);
    @Autowired
    private RoleRepository repo;

    @Autowired
    KeycloakUserExtension keycloak;


    @Override
    public PliotJpaRepository<UserGrp, String> getRepo() {
        return repo;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return UserGrpUtils.instance();
    }

    @Override
    public UserGrpTO create(UserGrpTO io) {

        return super.create(io);
    }



    @Override
    public UserGrpTO save(UserGrpTO io) {
     return super.save(io);
    }


}
