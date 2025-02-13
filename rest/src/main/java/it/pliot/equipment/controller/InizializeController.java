package it.pliot.equipment.controller;

import it.pliot.equipment.controller.io.InitializeSensorTO;
import it.pliot.equipment.io.SensorTO;

import it.pliot.equipment.service.business.InizializeServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InizializeController {

    private static final Logger log = LoggerFactory.getLogger(InizializeController.class);

    @Autowired
    private InizializeServices inizializeServices;

    @PostMapping("/init/sensor")
    public InitializeSensorTO initSensor(@RequestBody InitializeSensorTO initTo ){
        log.info( "init sensor " );
        SensorTO sensor = inizializeServices.inizialize( initTo.getSensor() , initTo.getEquipmentName() );
        initTo.setSensor( sensor );
        return initTo;

    }

}
