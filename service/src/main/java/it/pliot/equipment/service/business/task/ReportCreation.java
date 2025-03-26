package it.pliot.equipment.service.business.task;

import it.pliot.equipment.service.dbms.CallSPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ReportCreation {

    private static final Logger log = LoggerFactory.getLogger(ReportCreation.class);


    @Autowired
    CallSPService spService;

    private static final String update_report_data_first_stg = "update_report_data_first_stg";
    private static final String update_report_30_min = "update_report_20_min";
    private static final String update_report_1_h    = "update_report_1_hour";

    private Integer timeout_10_min  = 10 * 2;

    private Integer timeout_30_min  = 30 * 2;

    private Integer timeout_1_h     = 60 * 2;

    @Async
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void executeHoseKeeping() {
        log.info( " Aggregate 10 minutes " + this );
        try {
            spService.runStoreProcedureOnce(update_report_data_first_stg, timeout_10_min );
        }catch ( Exception e ){
            log.error( " error when call sp {} " , update_report_data_first_stg);
            log.error( e.getMessage() );

        }

    }
}
