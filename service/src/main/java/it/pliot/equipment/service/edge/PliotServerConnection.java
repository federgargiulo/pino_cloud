package it.pliot.equipment.service.edge;

import it.pliot.equipment.io.EdgeTO;
import it.pliot.equipment.io.PushDataResultTO;
import it.pliot.equipment.io.PushDataTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile( "edge" )
public interface PliotServerConnection {

    public EdgeTO registerEdge( EdgeTO requestBody );

    public PushDataResultTO pushData(  );
}
