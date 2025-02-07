package it.pliot.equipment.service.business.impl;

import it.pliot.equipment.io.SensorTO;
import it.pliot.equipment.model.Sensor;
import it.pliot.equipment.repository.SensorRepository;
import it.pliot.equipment.service.business.api.AddSensorService;
import it.pliot.equipment.service.dbms.util.ConvertUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
@Transactional

public class AddSensorServiceImpl implements AddSensorService {

    @Autowired
    private SensorRepository sensorRepository;
    public SensorTO addSensor (SensorTO sio){
        sio.setSensorId( UUID.randomUUID().toString() );
        Sensor s = ConvertUtils.senorIO2Data( sio );
        s = sensorRepository.save( s );
        return ConvertUtils.sensorData2IO( s );
    }


}
