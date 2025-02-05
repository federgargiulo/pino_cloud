package it.pliot.equipment.service.business.impl;

import it.pliot.equipment.io.EquipmentIO;
import it.pliot.equipment.io.SensorIO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.model.Sensor;
import it.pliot.equipment.repository.EquipmentRepository;
import it.pliot.equipment.repository.SensorRepository;
import it.pliot.equipment.service.business.api.AddSensorService;
import it.pliot.equipment.service.business.api.FindEquipmentByIdService;
import it.pliot.equipment.service.business.api.FindSensorByIdService;
import it.pliot.equipment.service.business.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindSensorByIdServiceImpl implements FindSensorByIdService {
    @Autowired
    private SensorRepository sensorRepository;


    @Override
    public SensorIO findById(String id) {

            Optional<Sensor> e = sensorRepository.findById(id);
            SensorIO eIO = ConvertUtils.sensorData2IO( e.get());
            return eIO;

    }
}
