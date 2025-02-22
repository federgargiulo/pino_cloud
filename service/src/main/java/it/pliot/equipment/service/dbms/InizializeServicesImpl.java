package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.io.SignalTO;
import it.pliot.equipment.service.business.EquipmentServices;
import it.pliot.equipment.service.business.InizializeServices;
import it.pliot.equipment.service.business.SignalServices;
import it.pliot.equipment.service.business.errors.ServiceExceptions;
import it.pliot.equipment.service.dbms.util.ConvertUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Transactional
public class InizializeServicesImpl implements InizializeServices {

    private static final Logger log = LoggerFactory.getLogger(InizializeServicesImpl.class);

    @Autowired
    private EquipmentServices equipment;

    @Autowired
    private SignalServices signalServices;

    private static String EQ_NAME = "Initialize Equipment";

    public SignalTO inizialize(SignalTO signal , String equipmentName){
        log.info( " create new signal " + signal.getName() );
        EquipmentTO eq = null;

        if ( signal == null || ConvertUtils.isNullOrEmpty( signal.getSignalId() ) )
            throw new ServiceExceptions( " signal  and signal id must not be null in the inialization phase ");

        SignalTO s = signalServices.findById( signal.getSignalId() );
        if ( s != null )
            return  s;


        if ( ConvertUtils.isNullOrEmpty( signal.getEquipmentId() ) ){
            eq = initEquipment(equipmentName);
            signal.setEquipmentId( eq.getEquipmentId() );

        }
        else{
            eq = equipment.findById( signal.getEquipmentId() );
            if ( eq == null )
                eq = initEquipment( equipmentName );
            signal.setEquipmentId( eq.getEquipmentId() );
            if ( eq == null )
                throw new ServiceExceptions(" Equipment id " + signal.getEquipmentId() + " does not exist ");
        }
        return signalServices.create( signal );

    }

    private EquipmentTO initEquipment(String equipmentName) {
        EquipmentTO eq;
        eq = new EquipmentTO();
        eq.setName( equipmentName == null ? EQ_NAME : equipmentName);
        eq = equipment.create( eq );
        return eq;
    }
}
