package it.pliot.equipment.conf;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile( "edge" )
public class EdgeConfiguration {

    private static final Logger log = LoggerFactory.getLogger(EdgeConfiguration.class);


    @PostConstruct
    public void initEdge(){
        log.info( " edge inizialization ");

    }
}
