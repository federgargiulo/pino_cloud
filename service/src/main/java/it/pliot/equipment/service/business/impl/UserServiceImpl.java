package it.pliot.equipment.service.business.impl;

import it.pliot.equipment.io.UserIO;
import it.pliot.equipment.repository.EquipmentRepository;
import it.pliot.equipment.service.business.UserServices;
import it.pliot.equipment.util.BaseConvertUtil;
import it.pliot.equipment.util.UserUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class UserServiceImpl extends BaseServiceImpl<UserIO,String> implements UserServices {

    @Autowired
    private EquipmentRepository userRepo;

    @Override
    public JpaRepository getRepo() {
        return userRepo;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return UserUtils.instance();
    }
}
