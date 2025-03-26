package it.pliot.equipment.service.business;

import it.pliot.equipment.io.EquipmentPullerTO;
import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.io.PagedResultTO;
import it.pliot.equipment.model.EquipmentPuller;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface EquipmentPullerServices extends BaseServiceInterface<EquipmentPullerTO,String> {



    public List<EquipmentPullerTO>  nextPulls();

    public List<EquipmentPullerTO> puller4Equipment( String equipmentId );

    public void startPull( String id );

    public void endPull( String id , String result  );


}
