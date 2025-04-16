package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.SyncCheckpointsTO;
import it.pliot.equipment.model.SyncCheckpoints;

public class SyncCheckpointsUtils extends BaseConvertUtil<SyncCheckpoints, SyncCheckpointsTO>{

    private static SyncCheckpointsUtils INSTANCE = new SyncCheckpointsUtils();
    public static BaseConvertUtil instance() {
        return INSTANCE;
    }

    @Override
    public SyncCheckpoints io2data(SyncCheckpointsTO in) {
        if ( in == null )
            return  null;
        SyncCheckpoints out = new SyncCheckpoints();
        out.setId( in.getId() );
        out.setLastTime( in.getLastTime() );
        return out;
    }

    @Override
    public SyncCheckpointsTO data2io(SyncCheckpoints in) {
        if ( in == null )
            return  null;
        SyncCheckpointsTO out = new SyncCheckpointsTO();
        out.setId( in.getId() );
        out.setLastTime( in.getLastTime() );
        return out;
    }
}
