package it.cogito.equipment.service;

import it.cogito.equipment.io.EquipmentIO;
import it.cogito.equipment.io.SensorIO;
import it.cogito.equipment.model.Equipment;
import it.cogito.equipment.model.Sensor;

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
        return  io;

    }

    public static SensorIO sensorData2IO( Sensor s ){
        if ( s==null)
            return  null;
        SensorIO io = new SensorIO();
        io.setSensorId( s.getSensorId() );
        io.setName( s.getName( ) ) ;
        io.setUnitOfMeasurement( s.getUnitOfMeasurement() );
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
        return  equipment;

    }


}
