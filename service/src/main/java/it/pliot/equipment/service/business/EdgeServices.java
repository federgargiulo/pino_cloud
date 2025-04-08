package it.pliot.equipment.service.business;

import it.pliot.equipment.io.EdgeTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!edge")
public interface EdgeServices {
    public EdgeTO registerEdge( EdgeTO edge );
}
