package it.pliot.equipment.service.business;


import it.pliot.equipment.io.SignalTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SignalServices extends BaseServiceInterface<SignalTO, String>{
    public List<SignalTO> getSignalsByEquipmentId(String equipmentId);
}
