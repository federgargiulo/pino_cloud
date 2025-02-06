package it.pliot.equipment.conf;

import it.pliot.equipment.io.EquipmentIO;
import it.pliot.equipment.io.RoleIO;
import it.pliot.equipment.io.SensorIO;
import it.pliot.equipment.io.TenantIO;
import it.pliot.equipment.service.business.EquipmentServices;
import it.pliot.equipment.service.business.RoleServices;
import it.pliot.equipment.service.business.SensorServices;
import it.pliot.equipment.service.business.TenanServices;
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
    private EquipmentServices equipmentService;

    @Autowired
    private SensorServices sensorServices;


    @PostConstruct
    public void initDb( ) {
        log.info("Preloading Role" + roleService.save( RoleIO.newroleio( "ADMIN" , "ADMINISTRATOR " ) ) );
        log.info("Preloading Role" + roleService.save(  RoleIO.newroleio( "USER" , "USER " ) ));
        log.info("Preloading Role" + roleService.save( RoleIO.newroleio( "TENAT_ADMIN" , "Tenant Administrator " ) ) );
        TenantIO t = tenanServices.save( TenantIO.newrtenant( "EDGE" , "EDGE TENANT" ) );

        log.info("Preloading Tenant" +  t ) ;

        EquipmentIO eq = equipmentService.create( EquipmentIO.newEquipment( "Pump" , "full descricption ") );
        log.info("Preloading  EquipmentIO" + eq );
        SensorIO s1 = SensorIO.newEmptyInstance( eq.getEquipmentId() , " TEMPERATURA ESTERNA");
        s1 = sensorServices.create( s1 );
        log.info("Preloading  Sensor" + s1 );

        EquipmentIO eq2 =equipmentService.create( EquipmentIO.newEquipment( "Inverter" , "full description ") );
        SensorIO s2 = SensorIO.newEmptyInstance( eq2.getEquipmentId() , "PRESSIONE_INTERNA");
        s1 = sensorServices.create( s2 );
        log.info("Preloading  Sensor" + s1 );


        log.info("Preloading " +  eq2 );

    }

}
