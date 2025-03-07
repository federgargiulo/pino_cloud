package it.pliot.equipment.repository;

import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.model.SystemConfiguration;
import jakarta.transaction.Transactional;

@Transactional
public interface SystemConfigurationRepository extends PliotJpaRepository<SystemConfiguration, String> {
}
