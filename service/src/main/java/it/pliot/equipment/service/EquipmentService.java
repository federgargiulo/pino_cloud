package it.pliot.equipment.service;

import it.pliot.equipment.io.EquipmentIO;
import it.pliot.equipment.io.SensorIO;

import java.util.List;

public interface EquipmentService {
    public List<EquipmentIO> all();
    public EquipmentIO create( EquipmentIO io );
    public EquipmentIO save( EquipmentIO io );
    public EquipmentIO findById (String id);
    public SensorIO addSensor (SensorIO sio);
}
