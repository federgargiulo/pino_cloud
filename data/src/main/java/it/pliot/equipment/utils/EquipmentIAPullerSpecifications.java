package it.pliot.equipment.utils;

import it.pliot.equipment.model.EquipmentIAPuller;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class EquipmentIAPullerSpecifications {

    public static Specification<EquipmentIAPuller> nexExecutions() {

        Date d = new Date();

        return ( Root<EquipmentIAPuller> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.and(  cb.lessThan( root.get( "nextExecutions"), d ) ,
                         cb.isNotNull( root.get( "lastEnd") ) );
        }

}

