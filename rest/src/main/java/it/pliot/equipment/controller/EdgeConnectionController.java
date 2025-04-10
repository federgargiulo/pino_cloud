package it.pliot.equipment.controller;


import it.pliot.equipment.conf.ApiPrefixController;
import it.pliot.equipment.service.edge.InizializeEdgeRespTO;
import it.pliot.equipment.io.EdgeTO;
import it.pliot.equipment.io.TenantTO;
import it.pliot.equipment.security.JwtUser;
import it.pliot.equipment.security.UserContext;
import it.pliot.equipment.service.business.EdgeServices;
import it.pliot.equipment.service.business.TenantServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@ApiPrefixController
@Profile("!edge")
public class EdgeConnectionController {

    private static final Logger log = LoggerFactory.getLogger(EquipmentController.class);

    @Autowired
    EdgeServices edgeServices;

    @Autowired
    TenantServices tenantServices;


    @PostMapping("/edge")
    public InizializeEdgeRespTO register(@RequestBody EdgeTO edge){

        String id = UUID.randomUUID().toString();
        JwtUser user = UserContext.currentUser();
        edge.setId( id );
        edge.setRegistrationDttm( new Date( ) );
        edge.setTenant( user.getTenantId() );

        edge =  edgeServices.save( edge );
        log.info( " added new edge " + edge.getId() );
        TenantTO t = tenantServices.findById( edge.getTenant() );
        log.info( " load temamt info  " + t.getTenantId() );
        InizializeEdgeRespTO respo = new InizializeEdgeRespTO( edge , t );
        return respo;

    }




}
