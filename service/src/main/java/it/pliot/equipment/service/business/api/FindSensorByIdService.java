package it.pliot.equipment.service.business.api;

import it.pliot.equipment.io.SensorTO;
import org.springframework.stereotype.Service;

@Service
@FunctionalInterface
public interface FindSensorByIdService {

    public SensorTO findById(String id );
}
