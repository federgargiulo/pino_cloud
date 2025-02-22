package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.TenantTO;
import it.pliot.equipment.model.Tenant;

public class ConvertUtils {


    public static boolean isNullOrEmpty( String x ){
        return  ( x == null || x.trim().length() == 0 );
    }
    public static long string2long( String x ){
        if ( x == null ) return 0;
        else return Long.parseLong( x );
    }


    public static Tenant tenantTO2Data(TenantTO tenantTO){

        if ( tenantTO == null )
            return null;
        Tenant tenant  = new Tenant();
        tenant.setId(  tenantTO.getId() );
        tenant.setName( tenantTO.getName() );
        tenant.setVersion( tenantTO.getVersion() );
        tenant.setCreatedDttm( tenantTO.getCreatedDttm() );
        tenant.setUpdateDttm( tenantTO.getUpdateDttm() );
        tenant.setDescription( tenantTO.getDescription() );
        return  tenant;

    }

    public static TenantTO data2TenantTO(Tenant tenant){

        if ( tenant == null )
            return null;
        TenantTO tenantTO  = new TenantTO();
        tenantTO.setId(  tenant.getId() );
        tenantTO.setName( tenant.getName() );
        tenantTO.setVersion( tenant.getVersion() );
        tenantTO.setCreatedDttm( tenant.getCreatedDttm() );
        tenantTO.setUpdateDttm( tenant.getUpdateDttm() );
        tenantTO.setDescription( tenant.getDescription() );
        return  tenantTO;

    }
}
