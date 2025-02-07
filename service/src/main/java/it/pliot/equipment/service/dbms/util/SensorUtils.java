package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.SensorTO;
import it.pliot.equipment.model.Sensor;

public class SensorUtils  extends BaseConvertUtil<Sensor, SensorTO>{

    private static SensorUtils INSTANCE = new SensorUtils();

    public static SensorUtils instance(){
        return INSTANCE;
    }
    private SensorUtils(){}


    @Override
    public Sensor io2data(SensorTO sio) {
        if ( sio == null ) return null;
        Sensor data = new Sensor();
        data.setName( sio.getName() );
        data.setId( sio.getSensorId( ) );
        data.setEquipmentId( sio.getEquipmentId() );
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

    @Override
    public SensorTO data2io(Sensor s) {
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
}
