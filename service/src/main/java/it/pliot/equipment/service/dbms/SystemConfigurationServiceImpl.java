package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.DatabaseSizeTO;
import it.pliot.equipment.io.SystemConfigurationTO;
import it.pliot.equipment.model.DashboardConfiguration;

import it.pliot.equipment.model.SystemConfiguration;
import it.pliot.equipment.repository.PliotJpaRepository;

import it.pliot.equipment.repository.SystemConfigurationRepository;

import it.pliot.equipment.service.business.SystemConfigurationService;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.SignalUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

@Component
@Transactional
public class SystemConfigurationServiceImpl  extends BaseServiceImpl<SystemConfigurationTO, DashboardConfiguration, String>
        implements SystemConfigurationService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private SystemConfigurationRepository repo;

    @Override
    public PliotJpaRepository<SystemConfiguration, String> getRepo() {
        return repo;
    }


    @Override
    public BaseConvertUtil getConverter() {
        return SignalUtils.instance();
    }





    public List<DatabaseSizeTO> getDatabaseSizes() {
        String sql = """
            SELECT datname, pg_size_pretty(pg_database_size(datname)) AS size
            FROM pg_database
            WHERE datistemplate = false;
        """;

        RowMapper<DatabaseSizeTO> mapper = (rs, rowNum) -> new DatabaseSizeTO(
                rs.getString("datname"),
                rs.getString("size")
        );

        return jdbcTemplate.query(sql, mapper);
    }


}
