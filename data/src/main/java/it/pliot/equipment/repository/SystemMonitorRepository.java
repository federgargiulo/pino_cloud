package it.pliot.equipment.repository;

import it.pliot.equipment.model.SystemHealthHistory;
import jakarta.transaction.Transactional;

@Transactional
public interface SystemMonitorRepository extends PliotJpaRepository<SystemHealthHistory, String> {
}
