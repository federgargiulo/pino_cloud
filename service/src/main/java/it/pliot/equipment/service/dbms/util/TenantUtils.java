package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.TenantTO;
import it.pliot.equipment.model.Tenant;

public class TenantUtils extends BaseConvertUtil<Tenant, TenantTO>{

    private static TenantUtils INSTANCE = new TenantUtils();

    public static TenantUtils instance(){
        return INSTANCE;
    }
    private TenantUtils(){}

    @Override
    public Tenant io2data(TenantTO in) {
        if ( in==null ) return null;
        Tenant out = new Tenant();
        out.setDescription( in.getDescription() );
        out.setId(in.getId());
        out.setName( in.getName());
        out.setCreatedDttm( in.getUpdateDttm() );
        out.setUpdateDttm( in.getUpdateDttm() );
        out.setVersion( in.getVersion() );
        return out;
    }

    @Override
    public TenantTO data2io(Tenant in) {
        if ( in==null ) return null;
        TenantTO out = new TenantTO();
        out.setDescription( in.getDescription() );
        out.setId(in.getId());
        out.setName( in.getName());
        out.setCreatedDttm( in.getUpdateDttm() );
        out.setUpdateDttm( in.getUpdateDttm() );
        out.setVersion( in.getVersion() );
        return out;

    }
}
