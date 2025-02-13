package it.pliot.equipment.controller;


import it.pliot.equipment.io.TenantTO;
import it.pliot.equipment.service.business.TenantServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TenantController {

    @Autowired
    public TenantServices tenantServices;

    @PostMapping("/tenant")
    public ResponseEntity<TenantTO> createTenant(@RequestBody TenantTO tenant) {
        try {
            TenantTO t = tenantServices.create( tenant);
            return new ResponseEntity<>(t, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/tenant/{id}")
    public TenantTO getTenantById(@PathVariable("id") String id) {
        return tenantServices.findById(id);

    }

    @DeleteMapping("/tenant/{id}")
    public void deleteTenant(@PathVariable("id") String id) {
        tenantServices.delete(id);
    }
}
