package it.pliot.equipment.conf;

import it.pliot.equipment.GlobalConfig;
import it.pliot.equipment.io.EdgeTO;
import it.pliot.equipment.io.ServerTO;
import it.pliot.equipment.service.business.EdgeServices;
import it.pliot.equipment.service.business.SystemServices;
import it.pliot.equipment.service.edge.PliotServerConnection;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!edge")
public class ServerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ServerConfiguration.class);

    @Autowired
    SystemServices sistemService;

    @PostConstruct
    public void initServer(){

        log.info( " Server initial configuration ");
        sistemService.inizialize();



    }
}
