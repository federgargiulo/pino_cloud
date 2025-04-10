package it.pliot.equipment.service.business;

import it.pliot.equipment.io.EdgeTO;
import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.io.SignalTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EdgeServices {

    public List<EdgeTO> findAll();

    public EdgeTO save( EdgeTO edge );
    public EdgeTO findById( String id );



}
