package it.pliot.equipment.service.business;

import it.pliot.equipment.io.DashboardConfigurationTO;
import it.pliot.equipment.io.PagedResultTO;

import java.util.List;


public interface DashboardConfigurationService extends BaseServiceInterface<DashboardConfigurationTO,String> {

    public List<DashboardConfigurationTO> findUserDashboards();
    public PagedResultTO<DashboardConfigurationTO> findAccessibleDashboardsOfCurrentUser(String page , int pageSize );

}
