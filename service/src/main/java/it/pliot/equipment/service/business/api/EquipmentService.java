package it.pliot.equipment.service.business.api;

import it.pliot.equipment.io.EquipmentIO;
import org.springframework.stereotype.Service;

@Service
public interface EquipmentService {
    public EquipmentIO create(EquipmentIO io );
}
