package it.pliot.equipment.service.business.api;

import it.pliot.equipment.io.TenantTO;
import org.springframework.stereotype.Service;

@Service
public interface TenantCRUDService {
    public TenantTO findById (String id);

    public TenantTO create(TenantTO io );

    public String  delete(String id);
}
