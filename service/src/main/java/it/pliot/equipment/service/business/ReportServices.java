package it.pliot.equipment.service.business;

import it.pliot.equipment.io.MeasureTO;
import it.pliot.equipment.io.ReportDataTO;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface ReportServices extends BaseServiceInterface<ReportDataTO, String>{

    Collection<Object> importFromEdge(List<ReportDataTO> reportData, String edegeId, Date d);
}
