package it.pliot.equipment.service.business;

import it.pliot.equipment.io.EdgeTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("!edge")
public interface EdgeServices {

    public List<EdgeTO> findAll();

    public EdgeTO save( EdgeTO edge );

}
