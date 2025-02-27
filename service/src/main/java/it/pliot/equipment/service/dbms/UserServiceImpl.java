package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.UserTO;
import it.pliot.equipment.model.User;
import it.pliot.equipment.repository.EquipmentRepository;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.repository.UserRepository;
import it.pliot.equipment.service.business.UserServices;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.UserUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class UserServiceImpl extends BaseServiceImpl<UserTO,User,String> implements UserServices {

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


}
