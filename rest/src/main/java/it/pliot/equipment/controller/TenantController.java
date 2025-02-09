package it.pliot.equipment.controller;


import it.pliot.equipment.io.TenantTO;
import it.pliot.equipment.service.business.TenantServices;
import it.pliot.equipment.service.business.api.TenantCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TenantController {

    @Autowired
    public TenantCRUDService tenantCRUDService;

    @PostMapping("/tenant")
    public ResponseEntity<TenantTO> createTenant(@RequestBody TenantTO tenant) {
        try {
            TenantTO t = tenantCRUDService.create( tenant);
            return new ResponseEntity<>(t, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/tenant/{id}")
    public TenantTO getTenantById(@PathVariable("id") String id) {
        return tenantCRUDService.findById(id);

    }

    @DeleteMapping("/tenant/{id}")
    public ResponseEntity<String> deleteTenant(@PathVariable("id") String id) {
         String idTenantDeleted = tenantCRUDService.delete(id);
        return new ResponseEntity<>(idTenantDeleted, HttpStatus.OK);
    }
}
