package it.pliot.equipment.conf;

import it.pliot.equipment.io.EquipmentIO;
import it.pliot.equipment.io.RoleIO;
import it.pliot.equipment.io.TenantIO;
import it.pliot.equipment.service.business.RoleServices;
import it.pliot.equipment.service.business.TenanServices;
import it.pliot.equipment.service.business.api.CreateEquipmentService;
import it.pliot.equipment.service.business.api.EquipmentService;
import it.pliot.equipment.service.business.impl.EquipmentServiceImpl;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class InitDb {

    private static final Logger log = LoggerFactory.getLogger(InitDb.class);

    @Autowired
    private RoleServices roleService;
    @Autowired
    private TenanServices tenanServices;

    @Autowired
    private EquipmentService equipmentService;


    @PostConstruct
    public void initDb( ) {
        log.info("Preloading Role" + roleService.save( RoleIO.newroleio( "ADMIN" , "ADMINISTRATOR " ) ) );
        log.info("Preloading Role" + roleService.save(  RoleIO.newroleio( "USER" , "USER " ) ));
        log.info("Preloading Role" + roleService.save( RoleIO.newroleio( "TENAT_ADMIN" , "Tenant Administrator " ) ) );
        TenantIO t = tenanServices.save( TenantIO.newrtenant( "EDGE" , "EDGE TENANT" ) );

        log.info("Preloading Tenant" +  t ) ;


        log.info("Preloading " + equipmentService.create(  EquipmentIO.newEquipment( "Pump" , "full descricption ")));
        log.info("Preloading " + equipmentService.create( EquipmentIO.newEquipment( "Inverter" , "full description ")));

    }

}
