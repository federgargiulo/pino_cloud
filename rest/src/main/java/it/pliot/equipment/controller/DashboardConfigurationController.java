package it.pliot.equipment.controller;


import it.pliot.equipment.conf.InitDb;
import it.pliot.equipment.io.DashboardConfigurationTO;
import it.pliot.equipment.security.UserContext;
import it.pliot.equipment.service.business.DashboardConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class DashboardConfigurationController {

    private static final Logger log = LoggerFactory.getLogger(DashboardConfigurationController.class);


    @Autowired
    DashboardConfigurationService dashboardConfServices;

    @GetMapping("/userdashboards")
    public List<DashboardConfigurationTO> userdashboards(){
        return dashboardConfServices.findUserDashboards();
    }

    @GetMapping("/userdashboards/{id}")
    public DashboardConfigurationTO userdashboardsById( @PathVariable("id") String id ){
        return dashboardConfServices.findById( id );
    }

    @PostMapping("/userdashboards")
    public DashboardConfigurationTO userdashboards(@RequestBody DashboardConfigurationTO toAdd ){
        toAdd.setUserId( UserContext.currentUser().getUserId() );
        log.info( " Created new Dashboard " + toAdd );
        return dashboardConfServices.save( toAdd );
    }
}
