package it.pliot.equipment.service.business;


import it.pliot.equipment.io.MeasureTO;
import it.pliot.equipment.io.PagedResultTO;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface MeasureServices extends BaseServiceInterface<MeasureTO, String>{


    public PagedResultTO search(String idSignal, Date from, String page , int pageSize );


}
