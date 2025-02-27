package it.pliot.equipment.repository;

import it.pliot.equipment.model.User;
import jakarta.transaction.Transactional;

@Transactional
public interface UserRepository extends PliotJpaRepository<User, String> {
}
