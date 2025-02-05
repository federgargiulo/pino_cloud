package it.pliot.equipment.service.business.api;

import it.pliot.equipment.io.EquipmentIO;
import org.springframework.stereotype.Service;

@Service
@FunctionalInterface
public interface FindEquipmentByIdService {
    public EquipmentIO findById (String id);
}
