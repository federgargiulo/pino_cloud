package it.pliot.equipment.service.business.impl;

import it.pliot.equipment.io.EquipmentIO;
import it.pliot.equipment.repository.EquipmentRepository;
import it.pliot.equipment.service.business.api.ReadEquipmentsService;
import it.pliot.equipment.service.business.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ReadEquipmentsServiceImpl implements ReadEquipmentsService {
    @Autowired
    private EquipmentRepository equipmentRepository;
    public List<EquipmentIO> all() {
        List<EquipmentIO> equipments =  ConvertUtils.equipmentListData2IO( equipmentRepository.findAll() );
        return  equipments;


    }
}
