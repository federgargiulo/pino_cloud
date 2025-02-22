package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.SignalTO;
import it.pliot.equipment.model.Signal;

public class SignalUtils extends BaseConvertUtil<Signal, SignalTO>{

    private static SignalUtils INSTANCE = new SignalUtils();

    public static SignalUtils instance(){
        return INSTANCE;
    }
    private SignalUtils(){}


    @Override
    public Signal io2data(SignalTO sio) {
        if ( sio == null ) return null;
        Signal data = new Signal();
        data.setName( sio.getName() );
        data.setId( sio.getSignalId( ) );
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
    public SignalTO data2io(Signal s) {
        if ( s==null)
            return  null;
        SignalTO io = new SignalTO();
        io.setSignalId( s.getId() );
        io.setEquipmentId( s.getEquipmentId() );
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
        io.setVersion( String.valueOf( s.getVersion() ) );
        return io;
    }
}
