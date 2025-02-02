package it.pliot.equipment.repository;

import it.pliot.equipment.model.Equipment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface EquipmentRepository extends JpaRepository<Equipment, String>{
}
