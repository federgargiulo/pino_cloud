package it.pliot.equipment.service;

import it.pliot.equipment.io.EquipmentIO;
import it.pliot.equipment.io.SensorIO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.model.Sensor;

import java.util.ArrayList;
import java.util.List;

public class ConvertUtils {

    public static List<EquipmentIO> equipmentListData2IO(List<Equipment> equipment ){
        List<EquipmentIO> eqio = new ArrayList<EquipmentIO>();
        if( equipment != null )
            equipment.forEach( e -> eqio.add( equipmentData2IO( e ) ) );
        return eqio;


    }

    public static EquipmentIO equipmentData2IO( Equipment equipment ){

        if ( equipment == null )
            return null;
        EquipmentIO io  = new EquipmentIO();
        io.setEquipmentId(  equipment.getEquipmentId() );
        io.setName( equipment.getName() );
        io.setSensors( sensorListData2IO( equipment.getSensors() ) );
        io.setCreatedDttm( equipment.getCreatedDttm() );
        io.setUpdateDttm( equipment.getUpdateDttm() );
        io.setVersion( equipment.getVersion() );

        return  io;

    }

    public static SensorIO sensorData2IO(Sensor s ){
        if ( s==null)
            return  null;
        SensorIO io = new SensorIO();
        io.setSensorId( s.getSensorId() );
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

    public static List<SensorIO> sensorListData2IO(List<Sensor> sensors ){
        ArrayList<SensorIO> sio = new ArrayList<SensorIO>();
        if ( sensors == null )
            return sio;
        sensors.forEach( s -> sio.add( sensorData2IO( s ) ) );
        return  sio;
    }


    public static Equipment equipmentIO2Data( EquipmentIO equipmentIO ){

        if ( equipmentIO == null )
            return null;
        Equipment equipment  = new Equipment();
        equipment.setEquipmentId(  equipmentIO.getEquipmentId() );
        equipment.setName( equipmentIO.getName() );
        equipment.setVersion( equipmentIO.getVersion() );
        return  equipment;

    }


    public static Sensor senorIO2Data(SensorIO sio) {
        if ( sio == null ) return null;
        Sensor data = new Sensor();
        data.setName( sio.getName() );
        data.setSensorId( sio.getSensorId( ) );
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
