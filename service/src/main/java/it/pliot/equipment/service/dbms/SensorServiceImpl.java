package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.SensorTO;
import it.pliot.equipment.model.Sensor;
import it.pliot.equipment.repository.SensorRepository;
import it.pliot.equipment.service.business.SensorServices;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.ConvertUtils;
import it.pliot.equipment.service.dbms.util.SensorUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Transactional
public class SensorServiceImpl extends BaseServiceImpl<SensorTO,String> implements SensorServices {

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
