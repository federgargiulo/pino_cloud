package it.pliot.equipment.service.edge;

import it.pliot.equipment.io.EdgeTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile( "edge" )
public interface PliotServerConnection {

    public EdgeTO registerEdge( EdgeTO requestBody );
}
