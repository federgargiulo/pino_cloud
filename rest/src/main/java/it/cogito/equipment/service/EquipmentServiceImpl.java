package it.cogito.equipment.service;

import it.cogito.equipment.io.EquipmentIO;
import it.cogito.equipment.model.Equipment;
import it.cogito.equipment.repository.EquipmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import it.cogito.equipment.service.ConvertUtils.*;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public class EquipmentServiceImpl implements  EquipmentService{

    @Autowired
    private EquipmentRepository equipmentRepository;

    public EquipmentRepository getEquipmentRepository() {
        return equipmentRepository;
    }

    public void setEquipmentRepository(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public List<EquipmentIO> all() {
        List<EquipmentIO> equipments =  ConvertUtils.equipmentListData2IO( equipmentRepository.findAll() );
        return  equipments;


    }

    public EquipmentIO create( EquipmentIO io ) {
        if ( io == null )
            throw new ServiceExceptions( "NULLIO");
        io.setEquipmentId( UUID.randomUUID().toString() );
        Equipment equipments =  ConvertUtils.equipmentIO2Data( io );
        equipments = equipmentRepository.save( equipments );
        return  ConvertUtils.equipmentData2IO( equipments ) ;


    }

    @Override
    public EquipmentIO findById(String id) {
        Optional<Equipment> e = equipmentRepository.findById(id);
        EquipmentIO eIO = ConvertUtils.equipmentData2IO( e.get());
        return eIO;
    }
}
