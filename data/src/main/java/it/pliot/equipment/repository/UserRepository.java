package it.pliot.equipment.repository;

import it.pliot.equipment.model.Tenant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface UserRepository extends JpaRepository<Tenant, String> {
}
