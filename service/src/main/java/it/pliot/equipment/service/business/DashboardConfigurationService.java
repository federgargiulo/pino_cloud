package it.pliot.equipment.service.business;

import it.pliot.equipment.io.DashboardConfigurationTO;

import java.util.List;


public interface DashboardConfigurationService extends BaseServiceInterface<DashboardConfigurationTO,String> {

    public List<DashboardConfigurationTO> findUserDashboards();
}
