package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.EquipmentPullerTO;
import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.io.PagedResultTO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.model.EquipmentPuller;
import it.pliot.equipment.repository.EquipmentRepository;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.service.business.EquipmentServices;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.EquipmentUtils;
import it.pliot.equipment.utils.EquipmentPullerSpecifications;
import it.pliot.equipment.utils.PushDataSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import jakarta.persistence.criteria.Predicate;

import java.util.*;

@Component
@Transactional
public class EquipmentServiceImpl extends BaseServiceImpl<EquipmentTO,Equipment ,String>
        implements EquipmentServices {

    @Autowired
    private EquipmentRepository repo;

    @Override
    public PliotJpaRepository<Equipment, String> getRepo() {
        return repo;
    }

    @Override
    public EquipmentTO create(EquipmentTO io) {
        if ( io.getEquipmentId() == null )
            io.setEquipmentId(UUID.randomUUID().toString());
        return super.create(io);
    }

    @Override
    public EquipmentTO save(EquipmentTO io) {
        if ( io.getEquipmentId() == null )
            io.setEquipmentId(UUID.randomUUID().toString());
        return super.save(io);
    }

    @Override
    public EquipmentTO update(EquipmentTO io) {
        return super.update(io);
    }

    @Override
    public BaseConvertUtil getConverter() {
        return EquipmentUtils.instance();
    }

    public List<EquipmentTO> findByTenant(String tenantId ){
      return findByTenantAndName( tenantId , null );
    }

    public List<EquipmentTO> findByTenantAndName(String tenantId, String name) {
        Specification<Equipment> spec = (root, query, builder) -> {
            var predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("tenant"), tenantId));
            predicates.add( builder.or( builder.isNull(root.get("status")), builder.notEqual(root.get("status"), "DELETED")));
            if (name != null) {
                predicates.add(builder.equal(root.get("name"), name));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };

        List<Equipment> equipments = getRepo().findAll(spec);
        return getConverter().converListData2IO(equipments);
    }

    @Override
    public Collection<Object> importFromEdge(List<EquipmentTO> equipments, String edgeId, Date d) {
        EquipmentUtils u = ( EquipmentUtils) getConverter();
        List<Equipment> edData = u.convertListIO2data( equipments , edgeId , d );
        edData = getRepo().saveAllAndFlush( edData );
        return Collections.singleton(u.converListData2IO(edData));
    }
    public List<EquipmentTO> findUpdatedEquipmentInTheInterval(Date from , Date to ){
        Pageable nextPage  = PageRequest.of( Integer.valueOf( 0 ) ,  100 );
        Specification<Equipment> spec = PushDataSpecifications.nextUpdatedEquipments( from , to );
        try {
            PagedResultTO<EquipmentTO> puller = findPaged(spec, nextPage);
            return  puller.getResults() ;
        }catch ( Exception e ){
            e.getStackTrace();
            throw  new RuntimeException( " errore in lettura " + e.getMessage() );
        }
    }

    public List<EquipmentTO> findAllNotDeleted() {
        Specification<Equipment> spec = (root, query, builder) -> builder.or(
                builder.isNull(root.get("status")),
                builder.notEqual(root.get("status"), "DELETED")
        );
        List<Equipment> equipments = getRepo().findAll(spec);
        return getConverter().converListData2IO(equipments);
    }


}
