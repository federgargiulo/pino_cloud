package it.pliot.equipment.repository;

import it.pliot.equipment.model.Edge;
import it.pliot.equipment.model.SyncCheckpoints;
import jakarta.transaction.Transactional;

@Transactional
public interface SyncCheckpointsRepository extends PliotJpaRepository<SyncCheckpoints, String> {
}
