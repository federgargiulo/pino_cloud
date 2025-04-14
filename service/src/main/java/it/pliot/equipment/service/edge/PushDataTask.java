package it.pliot.equipment.service.edge;

import it.pliot.equipment.Const;
import it.pliot.equipment.service.business.EquipmentServices;
import it.pliot.equipment.service.business.ReportServices;
import it.pliot.equipment.service.business.SignalServices;
import it.pliot.equipment.service.business.SyncCheckpointsServices;
import it.pliot.equipment.service.dbms.CronLockImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@Profile("edge")
public class PushDataTask {

    private static final Logger log = LoggerFactory.getLogger(PushDataTask.class);

    private Date STARTING_DTTM = null;

    @Autowired
    CronLockImpl lockServices;

    @Autowired
    SyncCheckpointsServices syncCheckpointsServices;

    @Autowired
    EquipmentServices equipmentServices;

    @Autowired
    SignalServices signalServices;

    @Autowired
    ReportServices reportServices;

    @Autowired
    PliotServerConnection pliotServerConnection;


    private static long MILLISEC_IN_A_MINUTE = 60 * 1000;

    private static long YEAR_AGO_MILLISEC    = 355 * 24 * 60 * 60 * 1000;


    private Date getXMinutesAgo( int x ) {

        Date d = new Date();
        return new Date( ( d.getTime() -  x * MILLISEC_IN_A_MINUTE ) );
    }


    private Date get10MinutesAgo(){
        return getXMinutesAgo( 10 );
    }

    public PushDataTask(){
        STARTING_DTTM = getXMinutesAgo( 355 * 24 * 60 );
    }


    @Async
    @Scheduled(fixedRate = 2, timeUnit = TimeUnit.MINUTES)
    public void movedata() {
        log.info( " Aggregate 10 minutes " + this );
        String processId = UUID.randomUUID().toString();
        try {
            if ( lockServices.acquireLock( Const.PUSH_VALUES_FROM_EDGE_TO_SERVER , 5 , processId ) ) {
                Object o = pliotServerConnection.pushData( );
                log.info( "sent {} " , o.toString() );
            }

        }finally {
            lockServices.release(Const.PUSH_VALUES_FROM_EDGE_TO_SERVER , processId );
        }

    }
}
