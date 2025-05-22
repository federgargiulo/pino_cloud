package it.pliot.equipment.service.business;

import it.pliot.equipment.io.BaseReportItemIO;

import it.pliot.equipment.io.DatabaseSizeTO;

import it.pliot.equipment.io.SystemHealthHistoryTO;

import java.util.List;

public interface SystemHealthHistoryService extends BaseServiceInterface<SystemHealthHistoryTO,String>{

    public static String DB_MEMORY_USAGE = "DB_MEMORY_USAGE";

    public List<DatabaseSizeTO> getCurrentDatabaseSizes();

    public void logDbSizes( );

    public List<SystemHealthHistoryTO> getDbmsMemoryUsageHistory();

    public List<BaseReportItemIO> getConnectionStatus();

    public List<BaseReportItemIO> getPodMemoryStatus();
}
