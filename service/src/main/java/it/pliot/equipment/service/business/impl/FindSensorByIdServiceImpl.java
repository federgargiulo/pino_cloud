package it.pliot.equipment.service.business.impl;

import it.pliot.equipment.io.SensorIO;
import it.pliot.equipment.model.Sensor;
import it.pliot.equipment.repository.SensorRepository;
import it.pliot.equipment.service.business.api.FindSensorByIdService;
import it.pliot.equipment.util.ConvertUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Transactional
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
