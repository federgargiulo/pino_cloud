package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.SignalTO;
import it.pliot.equipment.model.Signal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        data.setDownYellowLimit( sio.getDownYellowLimit() );
        data.setUpRedLimit( sio.getUpRedLimit() );
        data.setUpYellowLimit( sio.getUpYellowLimit());
        data.setTenant( sio.getTenant() );
        data.setStatus( sio.getStatus() );
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
        io.setTenant( s.getTenant() );
        io.setStatus( s.getStatus() );
        return io;
    }

    public List<Signal> convertListIO2data(List<SignalTO> lio, String edegeId, Date d) {
        ArrayList<Signal> ldaya = new ArrayList<>();
        if ( lio == null )
            return ldaya;
        lio.forEach(  o -> {
            Signal s =  io2data( o );
            s.setEdgeId( edegeId );
            s.setReceivedFromEdgeDttm( d );
            ldaya.add(s);
        } );
        return ldaya;
    }
}
