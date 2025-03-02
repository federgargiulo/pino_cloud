package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.MeasureTO;
import it.pliot.equipment.model.Measure;

public class MeasureUtils extends BaseConvertUtil<Measure, MeasureTO>{

    private static MeasureUtils INSTANCE = new MeasureUtils();

    private MeasureUtils(){}

    public static MeasureUtils instance(){
        return INSTANCE;
    }

    @Override
    public Measure io2data(MeasureTO in) {
        if ( in == null ) return  null;
        Measure out = new Measure();
        out.setId( in.getId() );
        out.setSrcId( in.getSrcId() );
        out.setTenantId( in.getTenantId() );
        out.setEquipmentId( in.getEquipmentId() );
        out.setMeasureDttm( in.getMeasureDttm() );
        out.setValore( in.getVal() );

        return out;
    }

    @Override
    public MeasureTO data2io(Measure in) {
        if ( in == null ) return  null;
        MeasureTO out = new MeasureTO();
        out.setId( in.getId() );
        out.setSrcId( in.getSrcId() );
        out.setEquipmentId( in.getEquipmentId() );
        out.setTenantId( in.getTenantId() );
        out.setMeasureDttm( in.getMeasureDttm() );
        out.setVal( in.getVal() );
        return out;
    }
}
