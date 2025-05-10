package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.SystemMonitorTO;
import it.pliot.equipment.model.SystemMonitor;

public class SystemMonitorUtil extends BaseConvertUtil<SystemMonitor, SystemMonitorTO> {


    private static final SystemMonitorUtil INSTANCE = new SystemMonitorUtil();

    @Override
    public SystemMonitor io2data(SystemMonitorTO in) {
        SystemMonitor out = new SystemMonitor();
        out.setDate(in.getDate());
        out.setMetrics( in.getMetrics() );
        out.setValue( in.getValue() );
        return out;
    }

    @Override
    public SystemMonitorTO data2io(SystemMonitor in) {
        SystemMonitorTO out = new SystemMonitorTO();
        out.setDate(in.getDate());
        out.setMetrics( in.getMetrics() );
        out.setValue( in.getValue() );
        return out;
    }

    public static SystemMonitorUtil instance() {
       return INSTANCE;
    }
}
