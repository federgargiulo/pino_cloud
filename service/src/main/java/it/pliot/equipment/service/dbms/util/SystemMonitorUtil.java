package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.SystemHealthHistoryTO;
import it.pliot.equipment.model.SystemHealthHistory;

public class SystemMonitorUtil extends BaseConvertUtil<SystemHealthHistory, SystemHealthHistoryTO> {


    private static final SystemMonitorUtil INSTANCE = new SystemMonitorUtil();

    @Override
    public SystemHealthHistory io2data(SystemHealthHistoryTO in) {
        SystemHealthHistory out = new SystemHealthHistory();
        out.setReport_dttm(in.getReport_dttm());
        out.setId( in.getId() );
        out.setComponent( in.getComponent() );
        out.setMetric( in.getMetric() );
        out.setVal( in.getVal() );
        out.setEdge( in.getEdge() );
        out.setTenat( in.getTenat() );
        return out;
    }

    @Override
    public SystemHealthHistoryTO data2io(SystemHealthHistory in) {
        SystemHealthHistoryTO out = new SystemHealthHistoryTO();
        out.setReport_dttm(in.getReport_dttm());
        out.setId( in.getId() );
        out.setComponent( in.getComponent() );
        out.setMetric( in.getMetric() );
        out.setVal( in.getVal() );
        out.setEdge( in.getEdge() );
        out.setTenat( in.getTenat() );
        return out;
    }

    public static SystemMonitorUtil instance() {
       return INSTANCE;
    }
}
