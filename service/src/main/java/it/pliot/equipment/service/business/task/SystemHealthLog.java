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
public class SystemHealthLog {

    private static final Logger log = LoggerFactory.getLogger(SystemHealthLog.class);

    @Autowired
    SystemHealthHistoryService healtService;


    @Async
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void loghealth() {
        List<DatabaseSizeTO> dbsizes = healtService.getCurrentDatabaseSizes();



    }

    }
