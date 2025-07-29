package it.pliot.equipment.service.business;

import it.pliot.equipment.io.EquipmentIAPullerTO;
import it.pliot.equipment.io.EquipmentPullerTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EquipmentIAPullerServices extends BaseServiceInterface<EquipmentIAPullerTO,String> {



    public List<EquipmentIAPullerTO>  nextPulls();

    public List<EquipmentIAPullerTO> iaPullers4Equipment( String equipmentId );

    public void startPull( String id );

    public void endPull( String id , String result  );


}
