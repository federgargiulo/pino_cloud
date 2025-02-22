package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.SignalTO;
import it.pliot.equipment.model.Signal;
import it.pliot.equipment.repository.SignalRepository;
import it.pliot.equipment.service.business.SignalServices;
import it.pliot.equipment.service.business.errors.ServiceExceptions;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.SignalUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Transactional
public class SignalServiceImpl extends BaseServiceImpl<SignalTO,String> implements SignalServices {

    @Autowired
    private SignalRepository repo;

    @Override
    public JpaRepository getRepo() {
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

}
