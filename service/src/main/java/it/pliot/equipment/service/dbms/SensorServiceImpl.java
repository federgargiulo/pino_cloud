package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.SensorIO;
import it.pliot.equipment.repository.SensorRepository;
import it.pliot.equipment.service.business.SensorServices;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.SensorUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class SensorServiceImpl extends BaseServiceImpl<SensorIO,String> implements SensorServices {

    @Autowired
    private SensorRepository repo;

    @Override
    public JpaRepository getRepo() {
        return repo;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return SensorUtils.instance();
    }
}
