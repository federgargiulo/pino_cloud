package it.pliot.equipment.service.business.impl;

import it.pliot.equipment.io.EquipmentIO;
import it.pliot.equipment.service.business.api.EquipmentService;
import it.pliot.equipment.service.business.api.UpdateEquipmentService;
import it.pliot.equipment.service.business.errors.ServiceExceptions;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class EquipmentServiceImpl implements EquipmentService {


    @Autowired
    private UpdateEquipmentService updateEquipment;

    public EquipmentIO create(EquipmentIO io ) {
        if ( io == null )
            throw new ServiceExceptions( "NULLIO");

        return updateEquipment.save( io );

    }
}
