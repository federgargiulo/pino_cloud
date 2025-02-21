package it.pliot.equipment.service.business;


import it.pliot.equipment.io.SensorTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SensorServices extends BaseServiceInterface<SensorTO, String>{
    public List<SensorTO> getSensorsByEquipmentId(String equipmentId);
}
