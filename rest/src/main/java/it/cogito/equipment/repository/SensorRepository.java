package it.cogito.equipment.repository;

import it.cogito.equipment.model.Equipment;
import it.cogito.equipment.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SensorRepository extends JpaRepository<Sensor, String>{
}
