package it.pliot.equipment.conf;

import it.pliot.equipment.Const;
import it.pliot.equipment.io.*;
import it.pliot.equipment.service.business.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class InitDb {

    private static final Logger log = LoggerFactory.getLogger(InitDb.class);

    @Autowired
    private UserGrpServices roleService;
    @Autowired
    private TenantServices tenanServices;

    @Autowired
    private EquipmentServices equipmentService;

    @Autowired
    private SignalServices signalServices;

    @Autowired
    private EquipmentPullerServices equipmentPullerService;


    @PostConstruct
    public void initDb( ) {
        log.info("Preloading Role" + roleService.save( UserGrpTO.newroleio( Const.ADMIN_GRP , "ADMINISTRATOR " ) ) );
        log.info("Preloading Role" + roleService.save(  UserGrpTO.newroleio(Const.USER_TENANT_GRP , "USER " ) ));
        log.info("Preloading Role" + roleService.save( UserGrpTO.newroleio( Const.TENANT_ADMIN_GRP  , "Tenant Administrator " ) ) );
        TenantTO t = tenanServices.create( TenantTO.newrtenant(Const.DEV_TENANT_ID , Const.DEV_TENANT_NAME , Const.DEV_TENANT_DESC, Const.DEV_EMAIL, Const.DEV_ADDRESS, Const.DEV_ZIPCODE, Const.DEV_COUNTRY,Const.DEV_PROFILE, Const.DEV_STATE ) );

        log.info("Preloading Tenant" +  t ) ;
        EquipmentTO eq = EquipmentTO.newEquipment( "Pump" , Const.DEV_TENANT_ID );

        createEquipmentAndRelations( eq );

        EquipmentTO eq2 = equipmentService.create( EquipmentTO.newEquipment( "Inverter" , Const.DEV_TENANT_ID ) );
        createEquipmentAndRelations( eq2 );
        log.info("Preloading " +  eq2 );

    }

    private void createEquipmentAndRelations(  EquipmentTO eq) {
        List<EquipmentTO> elEq = equipmentService.findByTenantAndName( eq.getTenant(), eq.getName() );
        System.out.println( " is emèty " + elEq.isEmpty() );
        if ( elEq != null && ! elEq.isEmpty() )
            return;


        eq = equipmentService.create( eq );
        log.info("Preloading  EquipmentIO" + eq );
        createSignal( eq , "test" , "PRESSIONE_INTERNA" );
        createSignal( eq , "test2" , "PRESSIONE_ESTERNA" );

        List<EquipmentPullerTO> eqList = equipmentPullerService.puller4Equipment( eq.getEquipmentId() );
        if ( eqList != null && !eqList.isEmpty() )
            return;

        Date now = new Date();
        EquipmentPullerTO puller = new EquipmentPullerTO();
        puller.setIdEquipment(eq.getEquipmentId());
        puller.setUrl("http://localhost:8000/data");
        puller.setApiKey("123456700");
        puller.setIntervalInSec( 20 );
        puller.setNextExecutions( now  );
        puller.setLastStart( now );
        puller.setLastEnd( now );
        puller.setTenant( eq.getTenant() );
        equipmentPullerService.save(puller);

    }

    private void createSignal( EquipmentTO eq2 , String signalId , String sigalDescr  ){

        SignalTO s = SignalTO.newEmptyInstance( eq2.getEquipmentId() , signalId, sigalDescr );
        s = signalServices.create( SignalTO.newEmptyInstance( eq2.getEquipmentId() , signalId, sigalDescr) );
        log.info("Preloading  Sensor" + s );
    }



}
