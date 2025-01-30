package it.cogito.equipment.repository;

import it.cogito.equipment.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EquipmentRepository extends JpaRepository<Equipment, String>{
}
