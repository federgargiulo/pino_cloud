package it.pliot.equipment.service.business.task;

import it.pliot.equipment.io.EquipmentPullerTO;
import it.pliot.equipment.service.business.EquipmentPullerServices;
import it.pliot.equipment.service.business.task.impl.PullerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class EquipmentPuller {

   @Autowired
   ApplicationContext context;

   @Autowired
   EquipmentPullerServices equipmentService;


   private static final Logger log = LoggerFactory.getLogger(EquipmentPuller.class);
   private static long MB = ( 1024*1024 );
   private static PullerImpl IMP_HTTP = new PullerImpl();


   @Async
   @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
   public void pullMeasure() {
      log.info( " pull measure ");
      List<EquipmentPullerTO> listOfPulls = equipmentService.nextPulls();
      if( listOfPulls != null &&  ! listOfPulls.isEmpty() ) {
         listOfPulls.forEach( x -> {
            IMP_HTTP.executeRetrieve( x , equipmentService , context );
         });
      }
      //log.info( b.toString() );

   }
}
