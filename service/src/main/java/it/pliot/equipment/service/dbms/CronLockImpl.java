package it.pliot.equipment.service.dbms;


import it.pliot.equipment.model.CronLock;
import it.pliot.equipment.repository.CronLockRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@Transactional
public class CronLockImpl {

    private static final Logger log = LoggerFactory.getLogger(CronLockImpl.class);

    @Autowired
    CronLockRepository repo;


    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public boolean acquireLock( String x , int minuteTimeout , String processId ){
        Optional<CronLock> optional = repo.findById( x );
        if ( optional.isPresent() ){
            return acquirePresent( optional.get() , minuteTimeout * 60 * 1000 , processId );
        }
        return initAndAcquire( x , processId );
    }

    private boolean acquirePresent(CronLock cronLock , long millisectimeout , String processId  ) {
        if ( cronLock.getLocked() ) {
            Date now = new Date();
            long lastrun = cronLock.getLastRun().getTime();
            if ( ( lastrun + millisectimeout ) > now.getTime() )
                return false;

            log.warn( " Gost log {} reaquired " , cronLock.getTask_name() );
            cronLock.setLastRun( now );
            cronLock.setProcessId( processId );
            return updateAndmanageOptimisticLock( cronLock );
        }

        cronLock.setLocked( Boolean.TRUE );
        cronLock.setLastRun( new Date() );
        cronLock.setProcessId( processId );
        return updateAndmanageOptimisticLock( cronLock );
    }

    private boolean updateAndmanageOptimisticLock(CronLock cronLock) {
        try {
            repo.save( cronLock );
            return true;
        }catch ( Exception e ){
            log.warn( e.getMessage() );
            log.warn( "cuncurrent acces to lock {} " , cronLock.getTask_name() );
            return false;
        }
    }

    private boolean initAndAcquire(String x , String processId ) {
        CronLock l = new CronLock();
        l.setTask_name( x );
        l.setLocked( Boolean.TRUE );
        l.setLastRun( new Date() );
        l.setProcessId( processId );

        return  updateAndmanageOptimisticLock( l );
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void release(String spName , String processId ) {
        Optional<CronLock> optional = repo.findById( spName );
        if ( optional.isPresent() ) {
            CronLock lock = optional.get();
            if ( processId.equals( lock.getProcessId() ) ) {
                lock.setLocked(Boolean.FALSE);
                lock.setProcessId( null );
                repo.save(lock);
            }
        }
    }
}
