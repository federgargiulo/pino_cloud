package it.pliot.equipment.service.business.impl;

import it.pliot.equipment.io.EquipmentIO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.repository.EquipmentRepository;
import it.pliot.equipment.service.business.api.UpdateEquipmentService;
import it.pliot.equipment.service.business.errors.ServiceExceptions;
import it.pliot.equipment.service.business.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateEquipmentServiceImpl implements UpdateEquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;
    public EquipmentIO save(EquipmentIO io ) {
        if ( io == null )
            throw new ServiceExceptions( "NULLIO");
        Equipment equipments =  ConvertUtils.equipmentIO2Data( io );
        equipments = equipmentRepository.save( equipments );
        return  ConvertUtils.equipmentData2IO( equipments ) ;


    }


}
