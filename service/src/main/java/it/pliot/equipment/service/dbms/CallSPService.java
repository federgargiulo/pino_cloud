package it.pliot.equipment.service.dbms;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CallSPService {

    private static final Logger log = LoggerFactory.getLogger(CallSPService.class);


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CronLockImpl lock;

    @Transactional
    @Async
    public void runStoreProcedureOnce( String spName , int timeoutMin   ) {

        String template = "CALL  %s()";
        String storedProcedureCall = String.format( template , spName );
        log.info( " call statement {}" , storedProcedureCall );
        Boolean isLocked = lock.acquireLock( spName , timeoutMin );

        if (isLocked != null && ! isLocked) {
            return;
        }
        try {
             jdbcTemplate.execute(storedProcedureCall);
        } catch (Exception e) {
           log.error( e.getMessage() );
        } finally {
            lock.release( spName );
        }
    }


}