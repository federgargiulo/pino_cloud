package it.pliot.equipment.conf;

import it.pliot.equipment.Const;
import it.pliot.equipment.GlobalConfig;
import it.pliot.equipment.Mode;
import it.pliot.equipment.io.*;
import it.pliot.equipment.service.business.*;
import it.pliot.equipment.service.ext.UserAlreadyPresentException;
import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;


@Component
public class InitDb {

    private static final Logger log = LoggerFactory.getLogger(InitDb.class);

    @Autowired
    private UserGrpServices usrGrp;
    @Autowired
    private TenantServices tenanServices;

    @Autowired
    private EquipmentServices equipmentService;

    @Autowired
    private SignalServices signalServices;

    @Autowired
    private EquipmentPullerServices equipmentPullerService;

    @Autowired
    private UserServices userServices;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private GlobalConfig config;



    private void executeSqlScripts() {
        log.info( "Executing db script ");
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:sql/init/*.sql");

            for (Resource resource : resources) {

                log.info( "Executing : {} " , resource.getFilename());
                String sql = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));
                jdbcTemplate.execute(sql);

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error( " Error on script execution ");
        }
    }

    public UserTO getAdmin(){
        UserTO u = new UserTO();
        u.setUserId( Const.ADMIN_PLIOT_USERID );
        u.setEmail( Const.ADMIN_PLIOT_MAIL );
        u.setLastName( Const.ADMIN_PLIOT_LASTNAME );
        u.setFirstName( Const.ADMIN_PLIOT_FIRSFNAME );
        u.setPassword( Const.DEFAULT_PASSWORD );
        u.setTenant( Const.ADMIN_PLIOT_TENANT );

        return u;
    }



    @PostConstruct
    public void initDb( ) {

        executeSqlScripts();



        UserGrpTO adminGrp = UserGrpTO.newroleio( Const.ADMIN_GRP , "ADMINISTRATOR " ) ;
        log.info("Preloading Group" + usrGrp.save(  adminGrp  ) );
        log.info("Preloading Group" + usrGrp.save( UserGrpTO.newroleio(Const.USER_TENANT_GRP , "USER " ) ));
        log.info("Preloading Group" + usrGrp.save( UserGrpTO.newroleio( Const.TENANT_ADMIN_GRP  , "Tenant Administrator " ) ) );
        if ( Mode.SERVER.equals( config.getMode() ) ) {
            log.info("Init admin user" );

            UserTO admin = getAdmin();
            ArrayList<UserGrpTO> grp = new ArrayList<UserGrpTO>();
            grp.add(adminGrp);
            admin.setUsrGrp(grp);
            try {
                userServices.create(admin);
            } catch (UserAlreadyPresentException e) {
                log.info(" user admin already created ");
            }
        }
        if ( ! config.isLoadEnabled() )
            return;

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
        puller.setEquipmentId(eq.getEquipmentId());
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
