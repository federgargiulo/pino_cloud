package it.pliot.equipment.repository;

import it.pliot.equipment.model.Edge;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;

@Profile("!edge")
@Transactional
public interface EdgeRepository extends PliotJpaRepository<Edge, String> {
}
