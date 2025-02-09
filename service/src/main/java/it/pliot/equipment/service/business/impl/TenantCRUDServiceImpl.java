package it.pliot.equipment.service.business.impl;

import it.pliot.equipment.io.TenantTO;
import it.pliot.equipment.model.Tenant;
import it.pliot.equipment.repository.TenantRepository;
import it.pliot.equipment.service.business.api.TenantCRUDService;
import it.pliot.equipment.service.business.errors.ServiceExceptions;
import it.pliot.equipment.service.dbms.util.ConvertUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Transactional
public class TenantCRUDServiceImpl implements TenantCRUDService {

    @Autowired
    private TenantRepository tenantRepository;
    @Override
    public TenantTO findById(String id) {
        Optional<Tenant> e = tenantRepository.findById(id);
        TenantTO eIO = ConvertUtils.data2TenantTO( e.get());
        return eIO;
    }

    @Override
    public TenantTO create(TenantTO io) {
        if ( io == null )
            throw new ServiceExceptions( "NULLIO");
        Tenant tenant = ConvertUtils.tenantTO2Data(io);
        tenant = tenantRepository.save(tenant);
        return ConvertUtils.data2TenantTO(tenant);
    }

    @Override
    public String  delete(String id) {
        if ( id == null )
            throw new ServiceExceptions( "NULLID");
        tenantRepository.deleteById(id);
        return id;
    }

}
