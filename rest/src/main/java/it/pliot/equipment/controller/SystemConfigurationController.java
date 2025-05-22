package it.pliot.equipment.controller;

import it.pliot.equipment.conf.ApiPrefixController;
import it.pliot.equipment.io.*;
import it.pliot.equipment.service.business.SystemConfigurationService;
import it.pliot.equipment.service.business.SystemHealthHistoryService;
import it.pliot.equipment.service.business.UserGrpServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@ApiPrefixController
public class SystemConfigurationController {


    private static final Logger log = LoggerFactory.getLogger(SystemConfigurationController.class);

    @Autowired
    SystemConfigurationService service;

    @Autowired
    SystemHealthHistoryService monitorServices;

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


    @GetMapping("/health/status")
    public HashMap<String, Object>  getDatabaseSizes() {


        List<SystemHealthHistoryTO> allLog = this.monitorServices.getDbmsMemoryUsageHistory();

        HashMap<String, Object> result = new HashMap<>();
        result.put( "DBMS" , allLog );
        result.put( "CONN_STATUS" , this.monitorServices.getConnectionStatus() );
        result.put( "POD_MEM" , this.monitorServices.getPodMemoryStatus() );

        return result;

    }

    private HashMap<String, List<SystemHealthHistoryTO>> putinhashtableformat(List<SystemHealthHistoryTO> allLog) {
        HashMap<String, List<SystemHealthHistoryTO>> result = new HashMap<>();
        allLog.forEach(
                log -> {
                        String k = log.getComponent();

                        List<SystemHealthHistoryTO> items = result.get( k );
                        if ( items == null ){
                            items = new ArrayList<>();
                            result.put( k , items );
                        }
                        items.add( log );
                }

        );
     return result;
    }


}
