package it.pliot.equipment.utils;

import it.pliot.equipment.model.Measure;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class MeasureSpecifications {

    public static Specification<Measure> isYoungerThan( Date instant) {
        return ( Root<Measure> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.greaterThan(root.get( "mesure_dttm"), instant );
    }

    public static Specification<Measure> isMeasureOfSignal( String signal) {
        return ( Root<Measure> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.get( "signalId"), signal );
    }

    public static Specification<Measure> isMeasureOfTenant( String tenantId) {
        return ( Root<Measure> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.get( "tenantId"), tenantId );
    }

}
