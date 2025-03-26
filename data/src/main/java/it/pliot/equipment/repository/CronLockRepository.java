package it.pliot.equipment.repository;

import it.pliot.equipment.model.CronLock;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;


@Transactional
public interface CronLockRepository extends JpaRepository<CronLock, String> {

}
