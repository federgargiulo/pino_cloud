package it.pliot.equipment.service.business;


import it.pliot.equipment.io.SignalTO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public interface SignalServices extends BaseServiceInterface<SignalTO, String>{
    public List<SignalTO> getSignalsByEquipmentId(String equipmentId);

    Collection<Object> importFromEdge(List<SignalTO> signals, String edegeId, Date d);

    List<SignalTO> findUpdatedSignalsInTheInterval(Date from, Date to);
}
