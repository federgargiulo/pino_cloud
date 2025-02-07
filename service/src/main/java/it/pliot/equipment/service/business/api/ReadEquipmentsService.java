package it.pliot.equipment.service.business.api;

import it.pliot.equipment.io.EquipmentTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@FunctionalInterface
public interface ReadEquipmentsService {
    public List<EquipmentTO> all();
}
