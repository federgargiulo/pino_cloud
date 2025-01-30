package it.cogito.equipment.repository;

import it.cogito.equipment.model.Equipment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface EquipmentRepository extends JpaRepository<Equipment, String>{
}
