package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.repository.EquipmentRepository;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.service.business.EquipmentServices;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.EquipmentUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
    public BaseConvertUtil getConverter() {
        return EquipmentUtils.instance();
    }

    public List<EquipmentTO> findByTenant(String tenantId ){
      return findByTenantAndName( tenantId , null );
    }

    public List<EquipmentTO> findByTenantAndName( String tenantId , String name){
        Equipment probe = new Equipment();
        probe.setTenant( tenantId );
        probe.setName( name );
        Example<Equipment> example = Example.of(probe);
        List<Equipment> equipments = getRepo().findAll(example);
        return getConverter().converListData2IO( equipments );
    }

    @Override
    public Collection<Object> importFromEdge(List<EquipmentTO> equipments, String edgeId, Date d) {
        EquipmentUtils u = ( EquipmentUtils) getConverter();
        List<Equipment> edData = u.convertListIO2data( equipments , edgeId , d );
        edData = getRepo().saveAllAndFlush( edData );
        return Collections.singleton(u.converListData2IO(edData));
    }

}
