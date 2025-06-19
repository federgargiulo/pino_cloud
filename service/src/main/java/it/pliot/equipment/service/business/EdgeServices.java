package it.pliot.equipment.service.business;

import it.pliot.equipment.io.EdgeTO;
import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.io.SignalTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EdgeServices extends BaseServiceInterface<EdgeTO,String> {

    public List<EdgeTO> findAll();

    public EdgeTO save( EdgeTO edge );

    public EdgeTO findById( String id );

    public EdgeTO register( EdgeTO edge );

    public List<EdgeTO> findByTenant( String tenant );



}
