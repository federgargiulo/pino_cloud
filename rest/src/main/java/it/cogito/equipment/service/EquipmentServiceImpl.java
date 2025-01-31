package it.cogito.equipment.service;

import it.cogito.equipment.io.EquipmentIO;
import it.cogito.equipment.io.SensorIO;
import it.cogito.equipment.model.Equipment;
import it.cogito.equipment.model.Sensor;
import it.cogito.equipment.repository.EquipmentRepository;
import it.cogito.equipment.repository.SensorRepository;
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

    @Autowired
    private SensorRepository sensorRepository;

    public SensorRepository getSensorRepository() {
        return sensorRepository;
    }

    public void setSensorRepository(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

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
        return save( io );

    }
    public EquipmentIO save( EquipmentIO io ) {
        if ( io == null )
            throw new ServiceExceptions( "NULLIO");
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

    public SensorIO addSensor (SensorIO sio){
        sio.setSensorId( UUID.randomUUID().toString() );
        Sensor s = ConvertUtils.senorIO2Data( sio );
        s = sensorRepository.save( s );
        return ConvertUtils.sensorData2IO( s );
    }
}
