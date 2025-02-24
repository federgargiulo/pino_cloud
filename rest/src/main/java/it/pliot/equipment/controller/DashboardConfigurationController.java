package it.pliot.equipment.controller;


import it.pliot.equipment.io.DashboardConfigurationTO;
import it.pliot.equipment.service.business.DashboardConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class DashboardConfigurationController {

    @Autowired
    DashboardConfigurationService dashboardConfServices;


    public List<DashboardConfigurationTO> userdashboards(){
        return dashboardConfServices.findUserDashboards();
    }
}
