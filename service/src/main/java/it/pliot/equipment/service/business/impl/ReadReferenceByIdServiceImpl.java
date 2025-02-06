package it.pliot.equipment.service.business.impl;

import it.pliot.equipment.io.EquipmentIO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.repository.EquipmentRepository;
import it.pliot.equipment.service.business.api.ReadReferenceByIdService;
import it.pliot.equipment.service.dbms.util.ConvertUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class ReadReferenceByIdServiceImpl implements ReadReferenceByIdService {

    @Autowired
    private EquipmentRepository equipmentRepository;
    @Override
    public EquipmentIO getReferenceById(String id) {
            Equipment e = equipmentRepository.getReferenceById(id);
            EquipmentIO eIO = ConvertUtils.equipmentData2IO( e);
            return eIO;
    }

}
