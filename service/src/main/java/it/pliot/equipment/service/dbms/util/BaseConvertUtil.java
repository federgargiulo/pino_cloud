package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.PagedResultTO;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseConvertUtil<K,IO> {
    // K data
    // O io
    public abstract K io2data( IO in);


    public abstract  IO data2io( K in );

    protected static String nullalize(String str) {
        if ( str == null | str.length() == 0 ) return null;
        return  str;
    }


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

    public PagedResultTO<IO> convertPage(Page<K> page ){
        PagedResultTO<IO> rsto = new PagedResultTO<IO>();
        page.get().forEach(x -> {
            IO e = data2io( x );
            rsto.addItem( e );
        });
        if ( page.hasNext() )
            rsto.setNextPage( String.valueOf( page.getNumber() + 1 ) );
        if (! page.isFirst() )
            rsto.setPrevPage( String.valueOf( page.getNumber() - 1 ));

        return rsto;
    }
}
