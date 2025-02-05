package it.pliot.equipment.service.business.impl;

import it.pliot.equipment.io.EquipmentIO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.repository.EquipmentRepository;
import it.pliot.equipment.service.business.api.CreateEquipmentService;
import it.pliot.equipment.service.business.api.UpdateEquipmentService;
import it.pliot.equipment.service.business.errors.ServiceExceptions;
import it.pliot.equipment.service.business.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class CreateEquipmentServiceImpl implements CreateEquipmentService {

    @Autowired
    private UpdateEquipmentService updateEquipment;

    public EquipmentIO create(EquipmentIO io ) {
        if ( io == null )
            throw new ServiceExceptions( "NULLIO");
        io.setEquipmentId( UUID.randomUUID().toString() );
        return updateEquipment.save( io );

    }


}
