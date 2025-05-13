package it.pliot.equipment.service.business.task;

import it.pliot.equipment.io.DatabaseSizeTO;
import it.pliot.equipment.service.business.SystemHealthHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

 @Component
public class MemoryMonitor {

    private static final Logger log = LoggerFactory.getLogger(MemoryMonitor.class);
    private static long MB = ( 1024*1024 );
    @Async
    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void executeHoseKeeping() {
        StringBuffer b = new StringBuffer();
        b.append( " Max memory [MB] ").append( Runtime.getRuntime().maxMemory() / MB );
        b.append( " Tot memory [MB] ").append( Runtime.getRuntime().totalMemory() / MB );
        b.append( " Free memory [MB] ").append( Runtime.getRuntime().freeMemory() / MB ).append( "\n");

        log.info( b.toString() );

    }


     @Autowired
     SystemHealthHistoryService healtService;


     @Async
     @Scheduled(fixedRate = 1 , timeUnit = TimeUnit.HOURS )
     public void loghealth() {
         log.info( " save memory status ");
         healtService.logDbSizes();
     }
}
