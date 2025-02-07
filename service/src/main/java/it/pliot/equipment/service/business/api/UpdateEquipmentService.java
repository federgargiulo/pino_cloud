package it.pliot.equipment.service.business.api;

import it.pliot.equipment.io.EquipmentTO;
import org.springframework.stereotype.Service;
@Service
@FunctionalInterface
public interface UpdateEquipmentService {

    public EquipmentTO save(EquipmentTO io );
}
