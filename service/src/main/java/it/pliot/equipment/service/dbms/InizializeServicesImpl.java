package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.io.SensorTO;
import it.pliot.equipment.model.Sensor;
import it.pliot.equipment.service.business.EquipmentServices;
import it.pliot.equipment.service.business.InizializeServices;
import it.pliot.equipment.service.business.SensorServices;
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
    private SensorServices sensorServices;

    private static String EQ_NAME = "Initialize Equipment";

    public SensorTO inizialize( SensorTO sensor , String equipmentName){
        log.info( " create new sensor " + sensor.getName() );
        EquipmentTO eq = null;

        if ( sensor == null || ConvertUtils.isNullOrEmpty( sensor.getSensorId() ) )
            throw new ServiceExceptions( " Sensor and Sensor id must not be null in the inialization phase ");

        SensorTO s = sensorServices.findById( sensor.getSensorId() );
        if ( s != null )
            return  s;


        if ( ConvertUtils.isNullOrEmpty( sensor.getEquipmentId() ) ){
            eq = initEquipment(equipmentName);
            sensor.setEquipmentId( eq.getEquipmentId() );

        }
        else{
            eq = equipment.findById( sensor.getEquipmentId() );
            if ( eq == null )
                eq = initEquipment( equipmentName );
            sensor.setEquipmentId( eq.getEquipmentId() );
            if ( eq == null )
                throw new ServiceExceptions(" Equipment id " + sensor.getEquipmentId() + " does not exist ");
        }
        return sensorServices.create( sensor );

    }

    private EquipmentTO initEquipment(String equipmentName) {
        EquipmentTO eq;
        eq = new EquipmentTO();
        eq.setName( equipmentName == null ? EQ_NAME : equipmentName);
        eq = equipment.create( eq );
        return eq;
    }
}
