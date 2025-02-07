package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.TenantTO;
import it.pliot.equipment.repository.TenantRepository;
import it.pliot.equipment.service.business.TenanServices;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.TenantUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class TenantServiceImpl extends BaseServiceImpl<TenantTO,String> implements TenanServices {

    @Autowired
    private TenantRepository repo;

    @Override
    public JpaRepository getRepo() {
        return repo;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return TenantUtils.instance();
    }

}
