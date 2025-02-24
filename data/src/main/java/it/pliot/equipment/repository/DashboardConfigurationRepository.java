package it.pliot.equipment.repository;

import it.pliot.equipment.model.DashboardConfiguration;
import it.pliot.equipment.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardConfigurationRepository  extends JpaRepository<DashboardConfiguration, String> {
}
