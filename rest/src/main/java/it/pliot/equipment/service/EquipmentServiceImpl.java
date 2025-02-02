package it.pliot.equipment.service;

import it.pliot.equipment.io.EquipmentIO;
import it.pliot.equipment.io.SensorIO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.model.Sensor;
import it.pliot.equipment.repository.EquipmentRepository;
import it.pliot.equipment.repository.SensorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

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
