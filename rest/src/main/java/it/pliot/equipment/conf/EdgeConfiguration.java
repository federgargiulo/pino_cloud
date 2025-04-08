package it.pliot.equipment.conf;

import it.pliot.equipment.io.EdgeTO;
import it.pliot.equipment.service.business.EdgeServerServices;
import it.pliot.equipment.service.business.EdgeServices;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Profile( "edge" )
public class EdgeConfiguration {

    private static final Logger log = LoggerFactory.getLogger(EdgeConfiguration.class);

    @Autowired
    EdgeServices edgeService;

    @Autowired
    EdgeServerServices edgeServerService;

    @Value("${pliot.edge.name}")
    private String edgeName;


    private String edgeUrl= "http://localhost:9080/server";


    @PostConstruct
    public void initEdge(){
        log.info( " edge initial configuration ");
        List<EdgeTO> edges = edgeService.findAll();
        if ( edges.size() > 0 )
            return;

        EdgeTO to = new EdgeTO();
        to.setEdgeName( edgeName );
        to.setEdgeUrl( edgeUrl );
        to = edgeServerService.registerEdge( to );
        edgeService.save( to );



    }
}
