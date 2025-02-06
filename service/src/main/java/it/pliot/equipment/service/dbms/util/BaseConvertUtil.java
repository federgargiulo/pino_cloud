package it.pliot.equipment.service.dbms.util;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseConvertUtil<K,IO> {
    // K data
    // O io
    public abstract K io2data( IO in);


    public abstract  IO data2io( K in );



    public  List<IO> converListData2IO( List<K> ldata  ){
        ArrayList<IO> liodata = new ArrayList<>();
        if ( ldata == null )
            return liodata;
        ldata.forEach(  o -> liodata.add( data2io( o ) ) );
        return liodata;
    }

    public  List<K> converListIO2data( List<IO> lio  ){
        ArrayList<K> ldaya = new ArrayList<>();
        if ( lio == null )
            return ldaya;
        lio.forEach(  o -> ldaya.add( io2data( o ) ) );
        return ldaya;
    }


}
