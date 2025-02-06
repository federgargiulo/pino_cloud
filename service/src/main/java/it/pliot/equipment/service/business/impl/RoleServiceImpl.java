package it.pliot.equipment.service.business.impl;

import it.pliot.equipment.io.RoleIO;
import it.pliot.equipment.repository.RoleRepository;
import it.pliot.equipment.service.business.BaseServiceInterface;
import it.pliot.equipment.service.business.RoleServices;
import it.pliot.equipment.util.BaseConvertUtil;
import it.pliot.equipment.util.RoleUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<RoleIO, String> implements RoleServices {

    @Autowired
    private RoleRepository repo;

    @Override
    public JpaRepository getRepo() {
        return repo;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return RoleUtils.instance();
    }
}
