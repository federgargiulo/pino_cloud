package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.io.SensorTO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.model.Sensor;

import java.util.ArrayList;
import java.util.List;

public class ConvertUtils {

    public static List<EquipmentTO> equipmentListData2IO(List<Equipment> equipment ){
        List<EquipmentTO> eqio = new ArrayList<EquipmentTO>();
        if( equipment != null )
            equipment.forEach( e -> eqio.add( equipmentData2IO( e ) ) );
        return eqio;


    }

    public static EquipmentTO equipmentData2IO(Equipment equipment ){

        if ( equipment == null )
            return null;
        EquipmentTO io  = new EquipmentTO();
        io.setEquipmentId(  equipment.getId() );
        io.setName( equipment.getName() );
        io.setTenant( equipment.getTenant() );
        io.setSensors( sensorListData2IO( equipment.getSensors() ) );
        io.setCreatedDttm( equipment.getCreatedDttm() );
        io.setUpdateDttm( equipment.getUpdateDttm() );
        io.setVersion( equipment.getVersion() );

        return  io;

    }

    public static SensorTO sensorData2IO(Sensor s ){
        if ( s==null)
            return  null;
        SensorTO io = new SensorTO();
        io.setSensorId( s.getId() );
        io.setName( s.getName( ) ) ;
        io.setUnitOfMeasurement( s.getUnitOfMeasurement() );
        io.setDownRedLimit( s.getDownRedLimit() );
        io.setDownYellowLimit( s.getDownYellowLimit() );
        io.setUpYellowLimit( s.getUpYellowLimit() );
        io.setUpRedLimit( s.getUpRedLimit());
        io.setMaxVal( s.getMaxVal() );
        io.setMinVal( s.getMinVal());
        io.setCreatedDttm( s.getCreatedDttm() );
        io.setUpdateDttm( s.getUpdateDttm() );
        io.setVersion( s.getVersion() );
        return io;
    }

    public static List<SensorTO> sensorListData2IO(List<Sensor> sensors ){
        ArrayList<SensorTO> sio = new ArrayList<SensorTO>();
        if ( sensors == null )
            return sio;
        sensors.forEach( s -> sio.add( sensorData2IO( s ) ) );
        return  sio;
    }


    public static Equipment equipmentIO2Data( EquipmentTO equipmentTO){

        if ( equipmentTO == null )
            return null;
        Equipment equipment  = new Equipment();
        equipment.setId(  equipmentTO.getEquipmentId() );
        equipment.setName( equipmentTO.getName() );
        equipment.setVersion( equipmentTO.getVersion() );
        equipment.setTenant( equipmentTO.getTenant() );
        return  equipment;

    }


    public static Sensor senorIO2Data(SensorTO sio) {
        if ( sio == null ) return null;
        Sensor data = new Sensor();
        data.setName( sio.getName() );
        data.setId( sio.getSensorId( ) );
        data.setEquipmentId( sio.getSensorId( ) );
        data.setUnitOfMeasurement( sio.getUnitOfMeasurement() );
        data.setDownRedLimit( sio.getDownRedLimit() );
        data.setMaxVal( sio.getMaxVal() );
        data.setMinVal( sio.getMinVal() );
        data.setDownRedLimit( sio.getDownRedLimit() );
        data.setDownRedLimit( sio.getDownRedLimit() );
        data.setUpRedLimit( sio.getUpRedLimit() );
        data.setUpYellowLimit( sio.getUpYellowLimit());
        return data;
    }
}
