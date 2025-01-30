package it.cogito.equipment.service;

import it.cogito.equipment.io.EquipmentIO;

import java.util.List;

public interface EquipmentService {
    public List<EquipmentIO> all();
    public EquipmentIO create( EquipmentIO io );
}
