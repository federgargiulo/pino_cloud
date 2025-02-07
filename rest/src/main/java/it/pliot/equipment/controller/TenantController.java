package it.pliot.equipment.controller;

import it.pliot.equipment.io.TenantTO;
import it.pliot.equipment.service.business.TenanServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TenantController {

    @Autowired
    public TenanServices tenanServices;

    @PostMapping("/tenant")
    public ResponseEntity<TenantTO> createTenant(@RequestBody TenantTO tenant) {
        try {
            TenantTO t = tenanServices.save( tenant);
            return new ResponseEntity<>(t, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
