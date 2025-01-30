package it.cogito.equipment.repository;

import it.cogito.equipment.model.Equipment;
import it.cogito.equipment.model.Sensor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface SensorRepository extends JpaRepository<Sensor, String>{
}
