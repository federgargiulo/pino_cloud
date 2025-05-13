package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.BaseReportItemIO;

import it.pliot.equipment.io.DatabaseSizeTO;
import it.pliot.equipment.io.SystemHealthHistoryTO;
import it.pliot.equipment.model.SystemHealthHistory;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.repository.SystemConfigurationRepository;
import it.pliot.equipment.service.business.SystemHealthHistoryService;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.ConvertUtils;
import it.pliot.equipment.service.dbms.util.SystemMonitorUtil;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class SystemHealthHistoryServiceImpl extends BaseServiceImpl<SystemHealthHistoryTO, SystemHealthHistory, String>
        implements SystemHealthHistoryService {

    private static final Logger log = LoggerFactory.getLogger(SystemHealthHistoryServiceImpl.class);


    @Autowired
    private JdbcTemplate jdbcTemplate;


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

    public List<DatabaseSizeTO> getCurrentDatabaseSizes() {
        String sql = """
            SELECT datname, pg_database_size(datname) / 1024 AS size
            FROM pg_database
            WHERE datistemplate = false;
        """;

        RowMapper<DatabaseSizeTO> mapper = (rs, rowNum) -> new DatabaseSizeTO(
                rs.getString("datname"),
                rs.getString("size")
        );

        return jdbcTemplate.query(sql, mapper);
    }

    public List<BaseReportItemIO> getConnectionStatus() {
        String sql = """
            SELECT state, count(*) as total
                 FROM pg_stat_activity
                 GROUP BY state
        """;

        RowMapper<BaseReportItemIO> mapper = (rs, rowNum) -> new BaseReportItemIO(
                rs.getString("state"),
                rs.getString( "total")
        );

        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<BaseReportItemIO> getPodMemoryStatus() {
        ArrayList<BaseReportItemIO> list = new ArrayList<BaseReportItemIO>();

        list.add( new BaseReportItemIO( "Max" , String.valueOf( Runtime.getRuntime().maxMemory()  ) )  );
        list.add( new BaseReportItemIO( "Tot" , String.valueOf( Runtime.getRuntime().totalMemory()  ) )  );
        list.add( new BaseReportItemIO( "Free" , String.valueOf( Runtime.getRuntime().freeMemory()  ) )  );
        return list;
    }

    public List<SystemHealthHistoryTO> getDbmsMemorySizeHistory() {
        String sql = """
            SELECT 'DB_MEMORY_USAGE' as type_m , report_dttm , sum(CAST(val AS integer) ) / 1024 AS size
            FROM system_health_history
            group by report_dttm;
        """;

        RowMapper<SystemHealthHistoryTO> mapper = (rs, rowNum) -> convertSystemHealtTO( rs );

        return jdbcTemplate.query(sql, mapper);
    }

    private SystemHealthHistoryTO convertSystemHealtTO(ResultSet rs) throws SQLException {
        SystemHealthHistoryTO to = new SystemHealthHistoryTO();
        to.setReport_dttm( ConvertUtils.sqldate2date( rs.getDate(  "report_dttm") ) );
        to.setVal( rs.getString( "size" ) );
        to.setMetric( "KB");
        to.setComponent("DBMS");

        return to;
    }


    public void logDbSizes(){
        log.info( " store db size status ");
        List<DatabaseSizeTO> sizes = getCurrentDatabaseSizes();
        Date d = new Date();
        sizes.forEach( x -> storeDbSize( x , d ) );
    }

    @Override
    public List<SystemHealthHistoryTO> getDbmsMemoryUsageHistory() {

        return getDbmsMemorySizeHistory();
    }

    private void storeDbSize(DatabaseSizeTO x, Date d) {
        SystemHealthHistory systemHealtItem  = new SystemHealthHistory();
        systemHealtItem.setMetric( DB_MEMORY_USAGE );
        systemHealtItem.setComponent( x.getDatabaseName() );
        systemHealtItem.setVal( x.getSize() );
        systemHealtItem.setTenat( "LOCAL");
        systemHealtItem.setEdge( "CORRENT");
        systemHealtItem.setReport_dttm( d );
        getRepo().save( systemHealtItem );
    }


}
