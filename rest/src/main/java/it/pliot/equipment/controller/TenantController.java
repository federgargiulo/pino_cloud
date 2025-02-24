package it.pliot.equipment.controller;


import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.io.TenantTO;
import it.pliot.equipment.security.UserContext;
import it.pliot.equipment.service.business.EquipmentServices;
import it.pliot.equipment.service.business.TenantServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TenantController {

    @Autowired
    public TenantServices tenantServices;


    @Autowired
    public EquipmentServices equipmentServices;



    @GetMapping("/tenants")
    public List<TenantTO> all() {
        return tenantServices.findAll();
    }



    @PostMapping("/tenants")
    public ResponseEntity<TenantTO> createTenant(@RequestBody TenantTO tenant) {
        try {
            TenantTO t = tenantServices.create( tenant);
            return new ResponseEntity<>(t, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/tenants/{id}")
    public TenantTO getTenantById(@PathVariable("id") String id) {
        return tenantServices.findById(id);

    }

    @DeleteMapping("/tenants/{id}")
    public void deleteTenant(@PathVariable("id") String id) {
        tenantServices.delete(id);
    }

    @PatchMapping("/tenants/{id}")
    public ResponseEntity<TenantTO> updateTenant(@PathVariable("id") String id , @RequestBody TenantTO tenant) {
        try {
            tenant.setId( id );
            tenant = tenantServices.save( tenant );
            return new ResponseEntity<>(tenant, HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tenants/curr/equipments")
    public List<EquipmentTO> getEquipmentsForCurrentTenant() {

        return equipmentServices.findByTenant(UserContext.currentUser().getTenantId() );

    }

}
