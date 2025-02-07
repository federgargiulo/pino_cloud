package it.pliot.equipment.conf;

import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.io.RoleTO;
import it.pliot.equipment.io.SensorTO;
import it.pliot.equipment.io.TenantTO;
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
        log.info("Preloading Role" + roleService.save( RoleTO.newroleio( "ADMIN" , "ADMINISTRATOR " ) ) );
        log.info("Preloading Role" + roleService.save(  RoleTO.newroleio( "USER" , "USER " ) ));
        log.info("Preloading Role" + roleService.save( RoleTO.newroleio( "TENAT_ADMIN" , "Tenant Administrator " ) ) );
        TenantTO t = tenanServices.save( TenantTO.newrtenant( "EDGE" , "EDGE TENANT" ) );

        log.info("Preloading Tenant" +  t ) ;

        EquipmentTO eq = equipmentService.create( EquipmentTO.newEquipment( "Pump" , "full descricption ") );
        log.info("Preloading  EquipmentIO" + eq );
        SensorTO s1 = SensorTO.newEmptyInstance( eq.getEquipmentId() , " TEMPERATURA ESTERNA");
        s1 = sensorServices.create( s1 );
        log.info("Preloading  Sensor" + s1 );

        EquipmentTO eq2 =equipmentService.create( EquipmentTO.newEquipment( "Inverter" , "full description ") );
        SensorTO s2 = SensorTO.newEmptyInstance( eq2.getEquipmentId() , "PRESSIONE_INTERNA");
        s1 = sensorServices.create( s2 );
        log.info("Preloading  Sensor" + s1 );


        log.info("Preloading " +  eq2 );

    }

}
