package it.pliot.equipment.repository;

import it.pliot.equipment.model.Signal;
import jakarta.transaction.Transactional;

@Transactional
public interface SignalRepository extends PliotJpaRepository<Signal, String> {
}
