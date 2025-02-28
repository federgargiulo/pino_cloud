package it.pliot.equipment.conf;

import it.pliot.equipment.Const;
import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.io.RoleTO;
import it.pliot.equipment.io.SignalTO;
import it.pliot.equipment.io.TenantTO;
import it.pliot.equipment.service.business.EquipmentServices;
import it.pliot.equipment.service.business.RoleServices;
import it.pliot.equipment.service.business.SignalServices;
import it.pliot.equipment.service.business.TenantServices;
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
    private TenantServices tenanServices;

    @Autowired
    private EquipmentServices equipmentService;

    @Autowired
    private SignalServices signalServices;


    @PostConstruct
    public void initDb( ) {
        log.info("Preloading Role" + roleService.save( RoleTO.newroleio( "ADMIN" , "ADMINISTRATOR " ) ) );
        log.info("Preloading Role" + roleService.save(  RoleTO.newroleio( "USER" , "USER " ) ));
        log.info("Preloading Role" + roleService.save( RoleTO.newroleio( "TENAT_ADMIN" , "Tenant Administrator " ) ) );
        TenantTO t = tenanServices.save( TenantTO.newrtenant(Const.DEV_TENANT_ID , Const.DEV_TENANT_NAME , Const.DEV_TENANT_DESC, Const.DEV_EMAIL, Const.DEV_ADDRESS, Const.DEV_ZIPCODE, Const.DEV_COUNTRY,Const.DEV_PROFILE, Const.DEV_STATE ) );

        log.info("Preloading Tenant" +  t ) ;

        EquipmentTO eq = equipmentService.create( EquipmentTO.newEquipment( "Pump" , Const.DEV_TENANT_ID ) );
        log.info("Preloading  EquipmentIO" + eq );
        createSignal( eq , "test" , "PRESSIONE_INTERNA" );
        createSignal( eq , "test2" , "PRESSIONE_ESTERNA" );


        EquipmentTO eq2 = equipmentService.create( EquipmentTO.newEquipment( "Inverter" , Const.DEV_TENANT_ID ) );
        createSignal( eq2 , "test3" , "TEMPERATURE" );
        createSignal( eq2 , "test4" , "VOLTAGGIO" );

        log.info("Preloading " +  eq2 );

    }
    private void createSignal( EquipmentTO eq2 , String signalId , String sigalDescr  ){

        SignalTO s = SignalTO.newEmptyInstance( eq2.getEquipmentId() , signalId, sigalDescr );
        s = signalServices.create( SignalTO.newEmptyInstance( eq2.getEquipmentId() , signalId, sigalDescr) );
        log.info("Preloading  Sensor" + s );
    }



}
