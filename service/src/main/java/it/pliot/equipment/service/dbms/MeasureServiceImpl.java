package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.AggregateResultTO;
import it.pliot.equipment.io.MeasureTO;

import it.pliot.equipment.io.PagedResultTO;
import it.pliot.equipment.model.Measure;
import it.pliot.equipment.repository.MeasureRepository;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.service.business.MeasureServices;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.MeasureUtils;
import it.pliot.equipment.utils.MeasureSpecifications;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Transactional
public class MeasureServiceImpl extends BaseServiceImpl<MeasureTO, Measure, String> implements MeasureServices {

    @Autowired
    private MeasureRepository repo;

    @Override
    public PliotJpaRepository<Measure, String> getRepo() {
        return repo;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return MeasureUtils.instance();
    }

    public PagedResultTO<MeasureTO> search(String idSignal, Date from, String page ,int pageSize ){
        Sort sort = Sort.by(Sort.Direction.ASC, "measureDttm"); // <-- Ordina per "timestamp" decrescente

        Pageable nextPage  = PageRequest.of( Integer.valueOf( page ) ,  pageSize , sort );
        Specification<Measure> spec = Specification
                .where( MeasureSpecifications.isYoungerThan( from ) )
                .and( MeasureSpecifications.isMeasureOfSignal( idSignal ) );


        try {
            return findPaged( spec , nextPage );
        }catch ( Exception e ){
            e.getStackTrace();
            throw  new RuntimeException( " errore in lettura ");
        }
    }


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AggregateResultTO> getAggregatedData(String level,
                                                     String sigal_id ,
                                                     Date from ,
                                                     Date end) {

        String groupBy;
        String select;
        String where =  " WHERE signal_id = ? AND reference_timestamp > ? AND  reference_timestamp < ? ";
        String orderBy = "";
        switch (level) {

            case "MONTH":
                select = " year_val || '-' || month_val ";
                groupBy = " GROUP BY year_val, month_val ";
                orderBy = " ORDER BY year_val asc , month_val asc ";

                break;
            case "DAY":
                select = " year_val || '-' || month_val || '-' || day_val ";
                groupBy = "  GROUP BY year_val, month_val , day_val ";
                orderBy = " ORDER BY year_val asc , month_val asc , day_val asc ";
                break;
            case "HOUR":
                select = " year_val || '-' || month_val || '-' || day_val || ':' || hour_val ";
                groupBy = "  GROUP BY year_val , month_val , day_val , hour_val ";
                orderBy = " ORDER BY year_val asc , month_val asc , day_val asc , hour_val asc ";

                break;
            case "ROW":
                select = " TO_CHAR(reference_timestamp, 'YYYY-MM-DD HH24:MI')  ";
                groupBy = " GROUP BY reference_timestamp ";
                orderBy = " ORDER BY reference_timestamp asc ";
                break;
            default:
                throw new IllegalArgumentException("Invalid level: " + level);
        }

            String sql = "SELECT " + select + " AS label, " +
                "max( max_val ) as max, " +
                "min( max_val ) as min , " +
                "AVG( mean_val ) as mean  " +
                "FROM report_data_first_stg " +
                where + groupBy + orderBy;
        return jdbcTemplate.query(sql, new Object[]{ sigal_id , from , end }, (rs, rowNum) -> new AggregateResultTO(
                    rs.getString("label"),
                    rs.getDouble("min"),
                    rs.getDouble("max"),
                    rs.getDouble("mean")
        ));

    }
}
