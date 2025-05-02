package it.pliot.equipment.controller;

import it.pliot.equipment.conf.ApiPrefixController;
import it.pliot.equipment.io.DashboardConfigurationTO;
import it.pliot.equipment.io.MeasureTO;
import it.pliot.equipment.io.SystemConfigurationTO;
import it.pliot.equipment.io.UserGrpTO;
import it.pliot.equipment.service.business.SystemConfigurationService;
import it.pliot.equipment.service.business.UserGrpServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@ApiPrefixController
public class SystemConfigurationController {


    private static final Logger log = LoggerFactory.getLogger(SystemConfigurationController.class);

    @Autowired
    SystemConfigurationService service;

    @PostMapping("/configuration")
    public ResponseEntity addConfiguration(@RequestBody(required = true) SystemConfigurationTO conf ) {
        try {
            log.info( "received bulkmeasure " + conf );

            service.save( conf  );
            return new ResponseEntity<>( HttpStatus.OK);
        }catch (Exception e) {
            log.error( e.getMessage() );
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/configuration")
    public ResponseEntity<List<SystemConfigurationTO>>getConfiguration( ) {
        try {
            log.info( "read all configuration" );
            List<SystemConfigurationTO> confi = service.findAll();
            return new ResponseEntity<>( confi , HttpStatus.OK);
        }catch (Exception e) {
            log.error( e.getMessage() );
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/configuration/{id}")
    public ResponseEntity<SystemConfigurationTO> systemConfigById(@PathVariable("id") String id ){
        try {
            log.info( "read configuration" );
            SystemConfigurationTO confi = service.findById( id );
            return new ResponseEntity<>( confi , HttpStatus.OK);
        }catch (Exception e) {
            log.error( e.getMessage() );
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Autowired
    UserGrpServices userGrpServices;

    @GetMapping("/configuration/allGroups")
    public List<UserGrpTO> getALlGroups(){
        return userGrpServices.findAll();
    }
}
