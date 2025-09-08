package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.io.PagedResultTO;
import it.pliot.equipment.io.SignalTO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.model.Signal;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.repository.SignalRepository;
import it.pliot.equipment.service.business.SignalServices;
import it.pliot.equipment.service.business.errors.ServiceExceptions;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.SignalUtils;
import it.pliot.equipment.utils.PushDataSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Transactional
public class SignalServiceImpl extends BaseServiceImpl<SignalTO, Signal, String> implements SignalServices {

    @Autowired
    private SignalRepository repo;

    @Override
    public PliotJpaRepository<Signal, String> getRepo() {
        return repo;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return SignalUtils.instance();
    }


    public SignalTO create(SignalTO io ){
        if ( io == null )
            throw new ServiceExceptions(" SignalTO must be not null ");

        if ( io.getSignalId() == null || "".equals(io.getSignalId()))
            io.setSignalId( UUID.randomUUID().toString() );

        return super.create(  io  );
    }


    public List<SignalTO> getSignalsByEquipmentId(String equipmentId) {
        Specification<Signal> spec = (root, query, cb) -> cb.and(
                cb.equal(root.get("equipmentId"), equipmentId),
                cb.isNotNull(root.get("id")),
                cb.notEqual(cb.trim(root.get("id")), ""), // id != ''
                cb.or(
                        cb.isNull(root.get("status")),
                        cb.notEqual(root.get("status"), "DELETED")
                )
        );

        List<Signal> signals = repo.findAll(spec);
        return SignalUtils.instance().converListData2IO(signals);
    }


    @Override
    public Collection<Object> importFromEdge(List<SignalTO> signals, String edegeId, Date d) {
        SignalUtils u = ( SignalUtils) getConverter();
        List<Signal> edData = u.convertListIO2data( signals , edegeId , d );
        edData = getRepo().saveAllAndFlush( edData );
        return Collections.singleton(u.converListData2IO(edData));
    }

    @Override
    public List<SignalTO> findUpdatedSignalsInTheInterval(Date from, Date to){
        Pageable nextPage  = PageRequest.of( Integer.valueOf( 0 ) ,  100 );
        Specification<Signal> spec = PushDataSpecifications.nextUpdatedSignals(  from , to );
        try {
            PagedResultTO<SignalTO> puller = findPaged(spec, nextPage);
            return  puller.getResults() ;
        }catch ( Exception e ){
            e.getStackTrace();
            throw  new RuntimeException( " errore in lettura " + e.getMessage() );
        }
    }
}
