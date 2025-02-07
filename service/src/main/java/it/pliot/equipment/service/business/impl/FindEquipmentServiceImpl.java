package it.pliot.equipment.service.business.impl;

import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.repository.EquipmentRepository;
import it.pliot.equipment.service.business.api.FindEquipmentByIdService;
import it.pliot.equipment.service.dbms.util.ConvertUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@Transactional
public class FindEquipmentServiceImpl implements FindEquipmentByIdService {

    @Autowired
    private EquipmentRepository equipmentRepository;
    @Override
    public EquipmentTO findById(String id) {
        Optional<Equipment> e = equipmentRepository.findById(id);
        EquipmentTO eIO = ConvertUtils.equipmentData2IO( e.get());
        return eIO;
    }
}
