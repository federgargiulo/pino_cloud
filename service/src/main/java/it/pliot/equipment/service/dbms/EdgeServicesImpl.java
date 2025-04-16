package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.EdgeTO;

import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.model.Edge;

import it.pliot.equipment.repository.EdgeRepository;
import it.pliot.equipment.repository.PliotJpaRepository;

import it.pliot.equipment.service.business.EdgeServices;

import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.EdgeUtils;
import it.pliot.equipment.service.ext.KeycloakTenantExtension;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;




@Component
@Transactional
public class EdgeServicesImpl extends BaseServiceImpl<EdgeTO, Edge,String>
        implements EdgeServices {

    private static final Logger log = LoggerFactory.getLogger(EdgeServicesImpl.class);


    @Autowired
    EdgeRepository edgeRepository;


    @Autowired
    KeycloakTenantExtension keycloak;


    @Override
    public PliotJpaRepository getRepo() {
        return edgeRepository;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return EdgeUtils.instance();
    }

    public EdgeTO register( EdgeTO edge ){
        log.info(" align keycloak ");
        keycloak.alignKeycloakOnEdgeRegistration( edge );
        return save( edge );
    }
}
