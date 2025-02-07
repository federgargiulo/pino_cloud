package it.pliot.equipment.service.business.api;

import it.pliot.equipment.io.SensorTO;
import org.springframework.stereotype.Service;

@Service
@FunctionalInterface
public interface AddSensorService {
    public SensorTO addSensor (SensorTO sio);

}
