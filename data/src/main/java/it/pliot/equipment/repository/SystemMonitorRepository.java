package it.pliot.equipment.repository;

import it.pliot.equipment.model.SystemMonitor;
import it.pliot.equipment.model.UserGrp;
import jakarta.transaction.Transactional;

@Transactional
public interface SystemMonitorRepository extends PliotJpaRepository<SystemMonitor, String> {
}
