package it.pliot.equipment.repository;

import it.pliot.equipment.model.UserGrp;
import jakarta.transaction.Transactional;

@Transactional
public interface RoleRepository extends PliotJpaRepository<UserGrp, String> {
}
