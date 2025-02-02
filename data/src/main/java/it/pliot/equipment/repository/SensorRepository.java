package it.pliot.equipment.repository;

import it.pliot.equipment.model.Sensor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface SensorRepository extends JpaRepository<Sensor, String>{
}
