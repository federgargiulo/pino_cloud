package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.MeasureIO;
import it.pliot.equipment.io.RoleIO;
import it.pliot.equipment.model.Measure;
import it.pliot.equipment.model.Role;
import it.pliot.equipment.service.business.MeasureServices;

public class MeasureUtils extends BaseConvertUtil<Measure, MeasureIO>{

    private static MeasureUtils INSTANCE = new MeasureUtils();

    private MeasureUtils(){}

    public static MeasureUtils instance(){
        return INSTANCE;
    }

    @Override
    public Measure io2data(MeasureIO in) {
        if ( in == null ) return  null;
        Measure out = new Measure();
        out.setId( in.getId() );
        out.setMesure_dttm( in.getMesure_dttm() );
        out.setValore( in.getVal() );
        out.setSensorId( in.getSensorId() );
        return out;
    }

    @Override
    public MeasureIO data2io(Measure in) {
        if ( in == null ) return  null;
        MeasureIO out = new MeasureIO();
        out.setId( in.getId() );
        out.setMesure_dttm( in.getMesure_dttm() );
        out.setVal( in.getVal() );
        out.setSensorId( in.getSensorId() );
        return out;
    }
}
