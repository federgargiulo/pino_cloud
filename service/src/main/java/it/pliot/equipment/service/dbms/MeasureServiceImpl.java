package it.pliot.equipment.service.dbms;

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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

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

        System.out.printf( "#" + page + "#" );
        Pageable nextPage  = PageRequest.of( Integer.valueOf( page ) ,  pageSize );
        Specification<Measure> spec = Specification
                .where( MeasureSpecifications.isYoungerThan( from ) )  // Prezzo > valore (AND)
                .and( MeasureSpecifications.isMeasureOfSignal( idSignal ) );

        try {
            return findPaged(spec, nextPage);
        }catch ( Exception e ){
            e.getStackTrace();
            throw  new RuntimeException( " errore in lettura ");
        }
    }



}
