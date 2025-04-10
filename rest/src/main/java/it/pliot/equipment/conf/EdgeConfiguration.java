package it.pliot.equipment.conf;

import it.pliot.equipment.GlobalConfig;
import it.pliot.equipment.io.EdgeTO;
import it.pliot.equipment.service.edge.InizializeEdgeRespTO;
import it.pliot.equipment.service.edge.PliotServerConnection;
import it.pliot.equipment.service.business.EdgeServices;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    GlobalConfig config;

    @Autowired
    PliotServerConnection edgeServerService;


    @PostConstruct
    public void initEdge(){

        String edgeName = config.getConfValue("pliot.edge.name" , "");
        String edgeUrl = config.getConfValue("pliot.edge.edge-url" , "");


        log.info( " edge initial configuration ");
        List<EdgeTO> edges = edgeService.findAll();
        if ( edges.size() > 0 )
            return;

        EdgeTO to = new EdgeTO();
        to.setEdgeName( edgeName );
        to.setEdgeUrl( edgeUrl );
        to = edgeServerService.registerEdge( to );
        log.info( " registered " + to );



    }
}
