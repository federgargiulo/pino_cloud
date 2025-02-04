package it.pliot.equipment.service.business.api;

import it.pliot.equipment.io.SensorIO;
import org.springframework.stereotype.Service;

@Service
@FunctionalInterface
public interface AddSensorService {
    public SensorIO addSensor (SensorIO sio);
}
