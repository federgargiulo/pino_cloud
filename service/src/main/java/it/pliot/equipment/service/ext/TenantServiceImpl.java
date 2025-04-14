package it.pliot.equipment.service.ext;

import it.pliot.equipment.GlobalConfig;
import it.pliot.equipment.io.TenantTO;
import it.pliot.equipment.model.Tenant;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.repository.TenantRepository;
import it.pliot.equipment.service.business.TenantServices;
import it.pliot.equipment.service.dbms.BaseServiceImpl;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.TenantUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class TenantServiceImpl extends BaseServiceImpl<TenantTO, Tenant , String> implements TenantServices {


    private static final Logger log = LoggerFactory.getLogger(TenantServiceImpl.class);

    @Autowired
    GlobalConfig config;

    @Autowired
    KeycloakTenantExtension keycloak;



    @Autowired
    private TenantRepository repo;

    @Override
    public PliotJpaRepository<Tenant, String> getRepo() {
        return repo;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return TenantUtils.instance();
    }

    @Override
    public TenantTO create(TenantTO io) {
        try {
              keycloak.alignKeycloakOnNewTenat( io );
              return super.create( io );
        } catch (Exception e) {
            log.error( " error when contact idp " + e.getMessage() );
            throw new RuntimeException( " Impossibile create il tenat " , e);
        }


    }
}
