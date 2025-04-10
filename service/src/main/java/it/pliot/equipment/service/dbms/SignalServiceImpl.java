package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.SignalTO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.model.Signal;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.repository.SignalRepository;
import it.pliot.equipment.service.business.SignalServices;
import it.pliot.equipment.service.business.errors.ServiceExceptions;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.EquipmentUtils;
import it.pliot.equipment.service.dbms.util.SignalUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
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

        if ( io.getSignalId() == null )
            io.setSignalId( UUID.randomUUID().toString() );

        return super.create(  io  );
    }

    public List<SignalTO> getSignalsByEquipmentId(String equipmentId) {
        Signal probe = new Signal();
        probe.setEquipmentId(equipmentId);
        Example<Signal> example = Example.of(probe);
        List<Signal> sens= getRepo().findAll(example);
        return SignalUtils.instance().converListData2IO(sens);
    }

    @Override
    public Collection<Object> importFromEdge(List<SignalTO> signals, String edegeId, Date d) {
        SignalUtils u = ( SignalUtils) getConverter();
        List<Signal> edData = u.convertListIO2data( signals , edegeId , d );
        edData = getRepo().saveAllAndFlush( edData );
        return Collections.singleton(u.converListData2IO(edData));
    }

}
