package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.SensorTO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.model.Sensor;
import it.pliot.equipment.repository.SensorRepository;
import it.pliot.equipment.service.business.SensorServices;
import it.pliot.equipment.service.business.errors.ServiceExceptions;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.ConvertUtils;
import it.pliot.equipment.service.dbms.util.SensorUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
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


    public SensorTO create( SensorTO io ){
        if ( io == null )
            throw new ServiceExceptions(" SensorTO must be not null ");

        if ( io.getSensorId() == null )
            io.setSensorId( UUID.randomUUID().toString() );

        return super.create(  io  );
    }

    public List<SensorTO> getSensorsByEquipmentId(String equipmentId) {
        SensorTO probe = new SensorTO();
        probe.setEquipmentId(equipmentId);
        Example<SensorTO> example = Example.of(probe);
        return getRepo().findAll(example);
    }

}
