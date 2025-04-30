package it.pliot.equipment.repository;

import it.pliot.equipment.model.Equipment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Transactional
public interface EquipmentRepository extends PliotJpaRepository<Equipment, String>, JpaSpecificationExecutor<Equipment> {
}
