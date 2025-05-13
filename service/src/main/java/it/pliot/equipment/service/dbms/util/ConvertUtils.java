package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.TenantTO;
import it.pliot.equipment.model.Tenant;

import java.util.Date;

public class ConvertUtils {


    public static boolean isNullOrEmpty( String x ){
        return  ( x == null || x.trim().length() == 0 );
    }
    public static long string2long( String x ){
        if ( x == null ) return 0;
        else return Long.parseLong( x );
    }


    public static Date sqldate2date(java.sql.Date date) {
        if ( date == null ) return null;
        return new Date( date.getTime() );
    }
}
