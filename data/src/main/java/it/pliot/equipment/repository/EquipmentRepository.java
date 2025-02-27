package it.pliot.equipment.repository;

import it.pliot.equipment.model.Equipment;
import jakarta.transaction.Transactional;

@Transactional
public interface EquipmentRepository
        extends PliotJpaRepository<Equipment, String> {
}
