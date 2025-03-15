package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.DashboardConfigurationTO;
import it.pliot.equipment.model.DashboardConfiguration;

public class DashboardConfigurationUtils extends BaseConvertUtil<DashboardConfiguration, DashboardConfigurationTO> {

    private static DashboardConfigurationUtils INSTANCE = new DashboardConfigurationUtils();


    public static DashboardConfigurationUtils instance(){
        return INSTANCE;
    }



    @Override
    public DashboardConfiguration io2data(DashboardConfigurationTO in) {
        if ( in == null )return null;
        DashboardConfiguration configuration = new DashboardConfiguration();
        configuration.setConfiguration( in.getConfiguration() );
        configuration.setDescr( in.getDescr() );
        configuration.setTitle( in.getTitle() );
        configuration.setUserIdpId( in.getUserIdpId() );
        configuration.setId( in.getId() );
        configuration.setShared( in.getShared() );
        configuration.setTenant( in.getTenant() );
        return configuration;
    }

    @Override
    public DashboardConfigurationTO data2io(DashboardConfiguration in) {
        if ( in == null )return null;
        DashboardConfigurationTO configuration = new DashboardConfigurationTO();
        configuration.setConfiguration( in.getConfiguration() );
        configuration.setDescr( in.getDescr() );
        configuration.setTitle( in.getTitle() );
        configuration.setUserIdpId( in.getUserIdpId() );
        configuration.setId( in.getId() );
        configuration.setShared( in.getShared() );
        configuration.setTenant( in.getTenant() );
        return configuration;
    }
}
