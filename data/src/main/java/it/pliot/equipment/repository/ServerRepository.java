package it.pliot.equipment.repository;

import it.pliot.equipment.model.Edge;
import it.pliot.equipment.model.Server;
import jakarta.transaction.Transactional;


@Transactional
public interface ServerRepository extends PliotJpaRepository<Server, String> {
}
