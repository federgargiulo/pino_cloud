package it.pliot.equipment.service.business.api;

import it.pliot.equipment.io.EquipmentIO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@FunctionalInterface
public interface ReadEquipmentsService {
    public List<EquipmentIO> all();
}
