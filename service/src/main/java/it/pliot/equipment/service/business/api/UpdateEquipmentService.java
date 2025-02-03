package it.pliot.equipment.service.business.api;

import it.pliot.equipment.io.EquipmentIO;
import org.springframework.stereotype.Service;
@Service
@FunctionalInterface
public interface UpdateEquipmentService {

    public EquipmentIO save(EquipmentIO io );
}
