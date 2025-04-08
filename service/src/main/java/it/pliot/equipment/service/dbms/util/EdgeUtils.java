package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.EdgeTO;
import it.pliot.equipment.io.EquipmentPullerTO;
import it.pliot.equipment.model.Edge;
import it.pliot.equipment.model.EquipmentPuller;

public class EdgeUtils extends BaseConvertUtil<Edge, EdgeTO>{

    private static EdgeUtils INSTANCE = new EdgeUtils();

    public static EdgeUtils instance(){
        return INSTANCE;
    }

    @Override
    public Edge io2data(EdgeTO in) {
        Edge out = new Edge();
        out.setId( in.getId() );
        out.setClient( in.getClient() );
        out.setEdgeName( in.getEdgeName() );
        out.setRegistrationDttm( in.getRegistrationDttm() );
        out.setTenant( in.getTenant() );
        return out;
    }

    @Override
    public EdgeTO data2io(Edge in) {
        EdgeTO out = new EdgeTO();
        out.setId( in.getId() );
        out.setClient( in.getClient() );
        out.setEdgeName( in.getEdgeName() );
        out.setRegistrationDttm( in.getRegistrationDttm() );
        out.setTenant( in.getTenant() );
        return out;
    }
}
