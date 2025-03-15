package it.pliot.equipment.controller;


import it.pliot.equipment.conf.ApiPrefixController;
import it.pliot.equipment.io.DashboardConfigurationTO;
import it.pliot.equipment.io.PagedResultTO;
import it.pliot.equipment.security.UserContext;
import it.pliot.equipment.service.business.DashboardConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@ApiPrefixController
public class DashboardConfigurationController {

    private static final Logger log = LoggerFactory.getLogger(DashboardConfigurationController.class);


    @Autowired
    DashboardConfigurationService dashboardConfServices;

    @GetMapping("/userdashboards")
    public List<DashboardConfigurationTO> userdashboards(){
        PagedResultTO<DashboardConfigurationTO> pagedResult = dashboardConfServices.findAccessibleDashboardsOfCurrentUser( "0" , 100);
        return pagedResult.getResults();
    }

    @GetMapping("/userdashboards/{id}")
    public DashboardConfigurationTO userdashboardsById( @PathVariable("id") String id ){
        return dashboardConfServices.findById( id );
    }

    @PostMapping("/userdashboards")
    public DashboardConfigurationTO userdashboards(@RequestBody DashboardConfigurationTO toAdd ){
        // use tenant user
        String tenant = Objects.requireNonNull(UserContext.currentUser()).getTenantId();
        toAdd.setTenant( tenant );

        toAdd.setId( null );
        toAdd.setUserIdpId( UserContext.currentUser().getIdpId() );
        log.info( " Created new Dashboard " + toAdd );
        return dashboardConfServices.save( toAdd );
    }

    @PutMapping("/userdashboards/{id}")
    public DashboardConfigurationTO updateDashboard(@PathVariable("id") String id ,
                                                    @RequestBody DashboardConfigurationTO toAdd ){
        toAdd.setId( id );
        toAdd.setUserIdpId( UserContext.currentUser().getIdpId() );
        log.info( " Update Dashboard " + toAdd );
        return dashboardConfServices.update( toAdd );
    }

    @DeleteMapping("/userdashboards/{id}")
    public void deleteById(@PathVariable("id") String id ){
        log.info( " Id to delete " + id);
        dashboardConfServices.delete( id );
    }
}
