package it.pliot.equipment.service.business;


import it.pliot.equipment.io.AggregateResultTO;
import it.pliot.equipment.io.MeasureTO;
import it.pliot.equipment.io.PagedResultTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface MeasureServices extends BaseServiceInterface<MeasureTO, String>{


    public PagedResultTO search(String idSignal, Date from, String page , int pageSize );

    public List<AggregateResultTO> getAggregatedData(String level,
                                                     String sigal_id ,
                                                     Date from ,
                                                     Date end);
}
