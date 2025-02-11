package it.pliot.equipment.service.business;

import it.pliot.equipment.io.SensorTO;
import org.springframework.stereotype.Service;

@Service
public interface InizializeServices {

    public SensorTO inizialize( SensorTO sensor , String equipmentName );
}
