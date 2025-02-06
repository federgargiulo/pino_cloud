package it.pliot.equipment.util;

import it.pliot.equipment.io.TenantIO;
import it.pliot.equipment.model.Tenant;

public class TenantUtils extends BaseConvertUtil<Tenant,  TenantIO>{

    private static TenantUtils INSTANCE = new TenantUtils();

    public static TenantUtils instance(){
        return INSTANCE;
    }
    private TenantUtils(){}

    @Override
    public Tenant io2data(TenantIO in) {
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
    public TenantIO data2io(Tenant in) {
        if ( in==null ) return null;
        TenantIO out = new TenantIO();
        out.setDescription( in.getDescription() );
        out.setId(in.getId());
        out.setName( in.getName());
        out.setCreatedDttm( in.getUpdateDttm() );
        out.setUpdateDttm( in.getUpdateDttm() );
        out.setVersion( in.getVersion() );
        return out;

    }
}
