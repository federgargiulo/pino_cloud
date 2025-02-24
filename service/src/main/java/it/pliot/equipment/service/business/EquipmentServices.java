package it.pliot.equipment.service.business;

import it.pliot.equipment.io.EquipmentTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EquipmentServices extends BaseServiceInterface<EquipmentTO,String> {

    public List<EquipmentTO> findByTenant( String tenantId );
}
