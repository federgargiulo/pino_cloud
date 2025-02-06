package it.pliot.equipment.repository;

import it.pliot.equipment.model.Measure;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface MeasureRepository extends JpaRepository<Measure, String> {
}
