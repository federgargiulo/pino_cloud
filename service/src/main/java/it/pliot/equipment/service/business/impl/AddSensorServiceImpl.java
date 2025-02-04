package it.pliot.equipment.service.business.impl;

import it.pliot.equipment.io.SensorIO;
import it.pliot.equipment.model.Sensor;
import it.pliot.equipment.repository.EquipmentRepository;
import it.pliot.equipment.repository.SensorRepository;
import it.pliot.equipment.service.business.api.AddSensorService;
import it.pliot.equipment.service.business.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class AddSensorServiceImpl implements AddSensorService {

    @Autowired
    private SensorRepository sensorRepository;
    public SensorIO addSensor (SensorIO sio){
        sio.setSensorId( UUID.randomUUID().toString() );
        Sensor s = ConvertUtils.senorIO2Data( sio );
        s = sensorRepository.save( s );
        return ConvertUtils.sensorData2IO( s );
    }
}
