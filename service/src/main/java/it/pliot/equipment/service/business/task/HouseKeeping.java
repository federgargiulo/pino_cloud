package it.pliot.equipment.service.business.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class HouseKeeping {

    private static final Logger log = LoggerFactory.getLogger(HouseKeeping.class);

    @Async
    @Scheduled(fixedRate = 60, timeUnit = TimeUnit.MINUTES)
    public void executeHoseKeeping() {
        log.info( " HouseKeepingExecutor " + this );

    }
}
