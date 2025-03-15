package it.pliot.equipment.utils;

import it.pliot.equipment.model.DashboardConfiguration;
import it.pliot.equipment.model.Measure;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class DashboardConfigurationSpecifications {

    public static Specification<DashboardConfiguration> isOwnedByUser(String userIdpId) {
        return (Root<DashboardConfiguration> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.get( "userIdpId"), userIdpId );
    }

    public static Specification<DashboardConfiguration> isSharedInAtenant(String tenantId) {
        return (Root<DashboardConfiguration> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.and( cb.equal(root.get( "tenant"), tenantId ) ,  cb.equal(root.get( "isShared"), Boolean.TRUE ) );
    }

}
