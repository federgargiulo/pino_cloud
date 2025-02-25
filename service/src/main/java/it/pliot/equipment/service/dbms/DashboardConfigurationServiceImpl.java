package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.DashboardConfigurationTO;
import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.model.DashboardConfiguration;
import it.pliot.equipment.model.Signal;
import it.pliot.equipment.repository.DashboardConfigurationRepository;
import it.pliot.equipment.repository.MeasureRepository;
import it.pliot.equipment.security.UserContext;
import it.pliot.equipment.service.business.DashboardConfigurationService;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.DashboardConfigurationUtils;
import it.pliot.equipment.service.dbms.util.MeasureUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
public class DashboardConfigurationServiceImpl
        extends BaseServiceImpl<DashboardConfigurationTO,String>
            implements DashboardConfigurationService {


    @Autowired
    private DashboardConfigurationRepository repo;

    @Override
    public JpaRepository getRepo() {
        return repo;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return DashboardConfigurationUtils.instance();
    }


    public List<DashboardConfigurationTO> findUserDashboards(){
        String userId = UserContext.currentUserId();
        DashboardConfiguration probe = new DashboardConfiguration();
        probe.setUserId( userId );
        Example<DashboardConfiguration> example = Example.of(probe);
        List<DashboardConfiguration> configurations= getRepo().findAll(example);
        return getConverter().converListData2IO( configurations );
    }

}
