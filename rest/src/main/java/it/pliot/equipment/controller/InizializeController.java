package it.pliot.equipment.controller;

import it.pliot.equipment.controller.io.InitializeSignalTO;
import it.pliot.equipment.io.SignalTO;

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
    public InitializeSignalTO initSignal(@RequestBody InitializeSignalTO initTo ){
        log.info( "init signal " );
        SignalTO signal = inizializeServices.inizialize( initTo.getSignalTO() , initTo.getEquipmentName() );
        initTo.setSignalTO( signal );
        return initTo;

    }

}
