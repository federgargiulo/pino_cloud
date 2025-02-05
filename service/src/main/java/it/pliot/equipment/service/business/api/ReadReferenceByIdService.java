package it.pliot.equipment.service.business.api;

import it.pliot.equipment.io.EquipmentIO;
import it.pliot.equipment.model.Equipment;
import org.springframework.stereotype.Service;

@Service
@FunctionalInterface
public interface ReadReferenceByIdService {
    public EquipmentIO getReferenceById (String id);
}
