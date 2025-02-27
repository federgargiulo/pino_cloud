package it.pliot.equipment.repository;

import it.pliot.equipment.model.Role;
import jakarta.transaction.Transactional;

@Transactional
public interface RoleRepository extends PliotJpaRepository<Role, String> {
}
