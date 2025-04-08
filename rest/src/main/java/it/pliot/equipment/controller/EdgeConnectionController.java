package it.pliot.equipment.controller;


import it.pliot.equipment.conf.ApiPrefixController;
import it.pliot.equipment.io.EdgeTO;
import it.pliot.equipment.service.business.EdgeServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@ApiPrefixController
@Profile("!edge")
public class EdgeConnectionController {

    private static final Logger log = LoggerFactory.getLogger(EquipmentController.class);

    @Autowired
    EdgeServices edgeServices;

    @PostMapping("/edge")
    public EdgeTO register(@RequestBody EdgeTO edge){
        // detect tenant

        return edgeServices.registerEdge( edge );

    }


}
