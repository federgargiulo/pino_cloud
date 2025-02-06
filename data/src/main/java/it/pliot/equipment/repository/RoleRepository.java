package it.pliot.equipment.repository;

import it.pliot.equipment.model.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface RoleRepository extends JpaRepository<Role, String> {
}
