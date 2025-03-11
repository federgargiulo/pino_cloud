package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.MeasureTO;
import it.pliot.equipment.io.SystemConfigurationTO;
import it.pliot.equipment.model.Measure;
import it.pliot.equipment.model.SystemConfiguration;

public class SystemConfigurationUtilsUtils extends BaseConvertUtil<SystemConfiguration, SystemConfigurationTO>{

    private static SystemConfigurationUtilsUtils INSTANCE = new SystemConfigurationUtilsUtils();

    private SystemConfigurationUtilsUtils(){}

    public static SystemConfigurationUtilsUtils instance(){
        return INSTANCE;
    }

    @Override
    public SystemConfiguration io2data(SystemConfigurationTO in) {
        if ( in == null ) return  null;
        SystemConfiguration out = new SystemConfiguration();
        out.setDescription( in.getDescription() );
        out.setConfigurationKey( in.getConfigurationKey() );
        out.setConfigurationValue( in.getConfigurationValue() );
        out.setVersion( in.getVersion() );
        return out;
    }

    @Override
    public SystemConfigurationTO data2io(SystemConfiguration in) {
        if ( in == null ) return  null;
        SystemConfigurationTO out = new SystemConfigurationTO();
        out.setDescription( in.getDescription() );
        out.setConfigurationKey( in.getConfigurationKey() );
        out.setConfigurationValue( in.getConfigurationValue() );
        out.setVersion( in.getVersion() );
        return out;

    }
}
