package it.pliot.equipment.service.business;

import it.pliot.equipment.io.SignalTO;
import org.springframework.stereotype.Service;

@Service
public interface InizializeServices {

    public SignalTO inizialize(SignalTO sensor , String equipmentName );
}
