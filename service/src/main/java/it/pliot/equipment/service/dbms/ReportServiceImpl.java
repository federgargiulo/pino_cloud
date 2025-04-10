package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.ReportDataTO;
import it.pliot.equipment.model.ReportDataFirstStg;
import it.pliot.equipment.model.Signal;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.repository.ReportDataFirstStgRepository;

import it.pliot.equipment.service.business.ReportServices;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.ReportDataUtils;
import it.pliot.equipment.service.dbms.util.SignalUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class ReportServiceImpl extends  BaseServiceImpl<ReportDataTO, ReportDataFirstStg, String>
        implements ReportServices {

    private static ReportDataUtils INSTANCE = new ReportDataUtils();


    @Autowired
    ReportDataFirstStgRepository repository;

    @Override
    public PliotJpaRepository getRepo() {
        return repository;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return INSTANCE;
    }

    @Override
    public Collection<Object> importFromEdge(List<ReportDataTO> reportData, String edegeId, Date d) {
        ReportDataUtils u = ( ReportDataUtils) getConverter();
        List<ReportDataFirstStg> edData = u.convertListIO2data( reportData , edegeId , d );
        edData = getRepo().saveAllAndFlush( edData );
        return Collections.singleton(u.converListData2IO( edData ));
    }
}
