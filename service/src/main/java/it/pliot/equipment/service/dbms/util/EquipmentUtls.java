package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.EquipmentIO;
import it.pliot.equipment.model.Equipment;

public class EquipmentUtls extends BaseConvertUtil<Equipment, EquipmentIO>{

    private static EquipmentUtls INSTANCE = new EquipmentUtls();

    public static EquipmentUtls instance(){
        return INSTANCE;
      }

    @Override
    public Equipment io2data(EquipmentIO equipmentIO) {
        if ( equipmentIO == null )
            return null;
        Equipment equipment  = new Equipment();
        equipment.setId(  equipmentIO.getEquipmentId() );
        equipment.setName( equipmentIO.getName() );
        equipment.setVersion( equipmentIO.getVersion() );
        equipment.setTenant( equipmentIO.getTenant() );
        return  equipment;
    }

    @Override
    public EquipmentIO data2io(Equipment equipment) {
        if ( equipment == null )
            return null;
        EquipmentIO io  = new EquipmentIO();
        io.setEquipmentId(  equipment.getId() );
        io.setName( equipment.getName() );
        io.setTenant( equipment.getTenant() );
        io.setCreatedDttm( equipment.getCreatedDttm() );
        io.setUpdateDttm( equipment.getUpdateDttm() );
        io.setVersion( equipment.getVersion() );
        return  io;
    }
}
