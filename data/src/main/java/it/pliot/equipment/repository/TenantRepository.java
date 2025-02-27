package it.pliot.equipment.repository;

import it.pliot.equipment.model.Tenant;
import jakarta.transaction.Transactional;

@Transactional
public interface TenantRepository  extends PliotJpaRepository<Tenant, String> {
}
