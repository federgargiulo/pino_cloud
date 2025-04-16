package it.pliot.equipment.utils;

import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.model.EquipmentPuller;
import it.pliot.equipment.model.ReportDataFirstStg;
import it.pliot.equipment.model.Signal;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class PushDataSpecifications {


    public static Specification<Equipment> nextUpdatedEquipments( Date from, Date to ) {

        return (Root<Equipment> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.and( cb.greaterThan( root.get( "updateDttm") , from  ) ,
                        cb.lessThan( root.get( "updateDttm"), to ) );
    }

    public static Specification<Signal> nextUpdatedSignals(Date from, Date to ) {

        return (Root<Signal> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.and( cb.lessThan( root.get( "updateDttm"), to ) ,
                        cb.greaterThan( root.get( "updateDttm") , from  ) );
    }

    public static Specification<ReportDataFirstStg> nextUpdatedReportDataFirstStg( Date from, Date to ) {

        return (Root<ReportDataFirstStg> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.and( cb.lessThan( root.get( "creationDttm"), to ) ,
                        cb.greaterThan( root.get( "creationDttm") , from  ) );
    }


}
