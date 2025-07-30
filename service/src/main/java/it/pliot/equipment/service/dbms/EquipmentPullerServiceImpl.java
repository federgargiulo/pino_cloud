package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.EquipmentPullerTO;
import it.pliot.equipment.io.PagedResultTO;
import it.pliot.equipment.model.EquipmentPuller;
import it.pliot.equipment.model.Signal;
import it.pliot.equipment.repository.EquipmentPullerRepository;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.service.business.EquipmentPullerServices;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.EquipmentPullerUtils;
import it.pliot.equipment.service.dbms.util.SignalUtils;
import it.pliot.equipment.utils.EquipmentPullerSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Transactional
public class EquipmentPullerServiceImpl
        extends BaseServiceImpl<EquipmentPullerTO, EquipmentPuller, String>
        implements EquipmentPullerServices {

    @Autowired
    private EquipmentPullerRepository repo;

    @Override
    public PliotJpaRepository<EquipmentPuller, String> getRepo() {
        return repo;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return EquipmentPullerUtils.instance();
    }


    @Override
    public List<EquipmentPullerTO> nextPulls() {
        Pageable nextPage  = PageRequest.of( Integer.valueOf( 0 ) ,  100 );
        Specification<EquipmentPuller> spec = EquipmentPullerSpecifications.nexExecutions();
        try {
            PagedResultTO<EquipmentPullerTO> puller = findPaged(spec, nextPage);
            return  puller.getResults() ;
        }catch ( Exception e ){
            e.getStackTrace();
            throw  new RuntimeException( " errore in lettura ");
        }

    }

    public List<EquipmentPullerTO> puller4Equipment( String equipmentId ) {

        EquipmentPuller probe = new EquipmentPuller();
        probe.setEquipmentId( equipmentId);
        probe.setPullType( null );
        Example<EquipmentPuller> example = Example.of(probe);
        List<EquipmentPuller> sens= getRepo().findAll(example);
        return getConverter().converListData2IO(sens);
    }




    public void startPull( String id ){

        EquipmentPuller data = getRepo().getReferenceById( id );
        data.setLastStart( new Date() );
        data.setLastEnd( null );
        data.setLastExecutionReport( null );
        getRepo().save( data );
    }

    public void endPull( String id , String result  ){
        Date d = new Date();
        long nextTime = d.getTime();
        EquipmentPuller data = getRepo().getReferenceById( id );
        if ( data.getIntervalInSec() == null )
            nextTime = nextTime + 60*1000;
        else
            nextTime = nextTime + data.getIntervalInSec() * 1000;
        data.setLastEnd( new Date() );
        data.setLastExecutionReport( result );
        data.setNextExecutions( new Date( nextTime ));

        getRepo().save( data );
    }

}
