package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.EquipmentIAPullerTO;
import it.pliot.equipment.io.PagedResultTO;
import it.pliot.equipment.model.EquipmentIAPuller;
import it.pliot.equipment.repository.EquipmentIAPullerRepository;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.service.business.EquipmentIAPullerServices;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.EquipmentIAPullerUtils;
import it.pliot.equipment.utils.EquipmentIAPullerSpecifications;
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
public class EquipmentIAPullerServiceImpl
        extends BaseServiceImpl<EquipmentIAPullerTO, EquipmentIAPuller, String>
        implements EquipmentIAPullerServices {

    @Autowired
    private EquipmentIAPullerRepository repo;

    @Override
    public PliotJpaRepository<EquipmentIAPuller, String> getRepo() {
        return repo;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return EquipmentIAPullerUtils.instance();
    }


    @Override
    public List<EquipmentIAPullerTO> nextPulls() {
        Pageable nextPage  = PageRequest.of( Integer.valueOf( 0 ) ,  100 );
        Specification<EquipmentIAPuller> spec = EquipmentIAPullerSpecifications.nexExecutions();
        try {
            PagedResultTO<EquipmentIAPullerTO> puller = findPaged(spec, nextPage);
            return  puller.getResults() ;
        }catch ( Exception e ){
            e.getStackTrace();
            throw  new RuntimeException( " errore in lettura ");
        }

    }

    public List<EquipmentIAPullerTO> iaPullers4Equipment( String equipmentId ) {

        EquipmentIAPuller probe = new EquipmentIAPuller();
        probe.setEquipmentId( equipmentId);
        Example<EquipmentIAPuller> example = Example.of(probe);
        List<EquipmentIAPuller> sens= getRepo().findAll(example);
        return getConverter().converListData2IO(sens);
    }




    public void startPull( String id ){

        EquipmentIAPuller data = getRepo().getReferenceById( id );
        data.setLastStart( new Date() );
        data.setLastEnd( null );
        data.setLastExecutionReport( null );
        getRepo().save( data );
    }

    public void endPull( String id , String result  ){
        Date d = new Date();
        long nextTime = d.getTime();
        EquipmentIAPuller data = getRepo().getReferenceById( id );
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
