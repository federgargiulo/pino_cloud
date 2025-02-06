package it.pliot.equipment.service.business.impl;

import it.pliot.equipment.io.TenantIO;
import it.pliot.equipment.io.UserIO;
import it.pliot.equipment.repository.EquipmentRepository;
import it.pliot.equipment.repository.TenantRepository;
import it.pliot.equipment.service.business.TenanServices;
import it.pliot.equipment.util.BaseConvertUtil;
import it.pliot.equipment.util.TenantUtils;
import it.pliot.equipment.util.UserUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class TenantServiceImpl extends BaseServiceImpl<TenantIO,String> implements TenanServices {

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
