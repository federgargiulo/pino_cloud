package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.SystemConfigurationTO;
import it.pliot.equipment.model.DashboardConfiguration;
import it.pliot.equipment.model.SystemConfiguration;
import it.pliot.equipment.repository.EdgeRepository;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.repository.SystemConfigurationRepository;
import it.pliot.equipment.service.business.SystemConfigurationService;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.SystemMonitorUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class SystemMonitorServiceImpl extends BaseServiceImpl<SystemConfigurationTO, SystemConfiguration, String>
        implements SystemConfigurationService {

    @Autowired
    SystemConfigurationRepository repo;

    @Override
    public PliotJpaRepository getRepo() {
        return repo;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return SystemMonitorUtil.instance();
    }
}
