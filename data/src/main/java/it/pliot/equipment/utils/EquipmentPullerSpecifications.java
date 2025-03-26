package it.pliot.equipment.utils;

import it.pliot.equipment.model.EquipmentPuller;
import it.pliot.equipment.model.Measure;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class EquipmentPullerSpecifications {

    public static Specification<EquipmentPuller> nexExecutions() {

        Date d = new Date();

        return ( Root<EquipmentPuller> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.and(  cb.lessThan( root.get( "nextExecutions"), d ) ,
                         cb.isNotNull( root.get( "lastEnd") ) );
        }

}

