package it.pliot.equipment.service.business.impl;

import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.service.business.api.CreateEquipmentService;
import it.pliot.equipment.service.business.api.UpdateEquipmentService;
import it.pliot.equipment.service.business.errors.ServiceExceptions;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class CreateEquipmentServiceImpl implements CreateEquipmentService {

    @Autowired
    private UpdateEquipmentService updateEquipment;

    public EquipmentTO create(EquipmentTO io ) {
        if ( io == null )
            throw new ServiceExceptions( "NULLIO");

        return updateEquipment.save( io );

    }


}
