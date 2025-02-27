package it.pliot.equipment.repository;

import it.pliot.equipment.model.Measure;
import jakarta.transaction.Transactional;

@Transactional
public interface MeasureRepository
        extends PliotJpaRepository<Measure, String> {
}
