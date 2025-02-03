package it.pliot.equipment.service.business.impl;

import it.pliot.equipment.io.EquipmentIO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.repository.EquipmentRepository;
import it.pliot.equipment.service.business.api.FindEquipmentService;
import it.pliot.equipment.service.business.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class FindEquipmentServiceImpl implements FindEquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;
    @Override
    public EquipmentIO findById(String id) {
        Optional<Equipment> e = equipmentRepository.findById(id);
        EquipmentIO eIO = ConvertUtils.equipmentData2IO( e.get());
        return eIO;
    }
}
