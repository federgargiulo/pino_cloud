package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.io.ReportDataTO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.model.ReportDataFirstStg;
import it.pliot.equipment.model.Signal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportDataUtils extends BaseConvertUtil<ReportDataFirstStg, ReportDataTO>{


    @Override
    public ReportDataFirstStg io2data(ReportDataTO in) {
        if ( in == null ) return  null;
        ReportDataFirstStg r = new ReportDataFirstStg();
        r.setEdgeId( in.getEdgeId() );
        r.setEquipmentId( in.getEquipmentId());
        r.setDayVal( in.getDayVal() );
        r.setCreationDttm( in.getCreationDttm() );
        r.setHourVal( in.getHourVal() );
        r.setCountOfMeasure( in.getCountOfMeasure() );
        r.setMaxVal( in.getMaxVal() );
        r.setMeanVal( in.getMeanVal() );
        r.setMinuteVal( in.getMinuteVal());
        r.setMinVal( in.getMinVal() );
        r.setMonthVal( in.getMonthVal() );
        r.setReceivedFromEdgeDttm( in.getReceivedFromEdgeDttm() );
        r.setReferenceTimestamp( in.getReferenceTimestamp());
        r.setSignalId( in.getSignalId());
        r.setTenantId( in.getTenantId() );
        r.setYearVal( in.getYearVal() );
        r.setId( in.getId());


        return r;
    }

    @Override
    public ReportDataTO data2io(ReportDataFirstStg in) {
        if ( in == null ) return null;

        ReportDataTO r = new ReportDataTO();
        r.setEdgeId( in.getEdgeId() );
        r.setEquipmentId( in.getEquipmentId());
        r.setDayVal( in.getDayVal() );
        r.setCreationDttm( in.getCreationDttm() );
        r.setHourVal( in.getHourVal() );
        r.setCountOfMeasure( in.getCountOfMeasure() );
        r.setMaxVal( in.getMaxVal() );
        r.setMeanVal( in.getMeanVal() );
        r.setMinuteVal( in.getMinuteVal());
        r.setMinVal( in.getMinVal() );
        r.setMonthVal( in.getMonthVal() );
        r.setReceivedFromEdgeDttm( in.getReceivedFromEdgeDttm() );
        r.setReferenceTimestamp( in.getReferenceTimestamp());
        r.setSignalId( in.getSignalId());
        r.setTenantId( in.getTenantId() );
        r.setYearVal( in.getYearVal() );
        r.setId( in.getId());
        return r;
    }

    public List<ReportDataFirstStg> convertListIO2data(List<ReportDataTO> lio, String edegeId, Date d) {
        ArrayList<ReportDataFirstStg> ldaya = new ArrayList<>();
        if ( lio == null )
            return ldaya;
        lio.forEach(  o -> {
            ReportDataFirstStg s =  io2data( o );
            s.setEdgeId( edegeId );
            s.setReceivedFromEdgeDttm( d );
            ldaya.add(s);
        } );
        return ldaya;
    }
}
