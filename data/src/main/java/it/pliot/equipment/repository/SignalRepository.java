package it.pliot.equipment.repository;

import it.pliot.equipment.model.Signal;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface SignalRepository extends JpaRepository<Signal, String>{
}
