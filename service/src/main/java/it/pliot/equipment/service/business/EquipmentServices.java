package it.pliot.equipment.service.business;

import it.pliot.equipment.io.EquipmentTO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public interface EquipmentServices extends BaseServiceInterface<EquipmentTO,String> {

    public List<EquipmentTO> findByTenant( String tenantId );

    public List<EquipmentTO> findByTenantAndName( String tenantId , String name);

    Collection<Object> importFromEdge(List<EquipmentTO> equipments, String edegeId, Date d);

    public List<EquipmentTO> findUpdatedEquipmentInTheInterval(Date from , Date to );

    public List<EquipmentTO> findAllNotDeleted();
    public EquipmentTO updateStatus(String equipmentId , String status );
}
