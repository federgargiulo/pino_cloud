package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.model.Equipment;

public class EquipmentUtls extends BaseConvertUtil<Equipment, EquipmentTO>{

    private static EquipmentUtls INSTANCE = new EquipmentUtls();

    public static EquipmentUtls instance(){
        return INSTANCE;
      }

    @Override
    public Equipment io2data(EquipmentTO equipmentTO) {
        if ( equipmentTO == null )
            return null;
        Equipment equipment  = new Equipment();
        equipment.setId(  equipmentTO.getEquipmentId() );
        equipment.setName( equipmentTO.getName() );
        equipment.setVersion( equipmentTO.getVersion() );
        equipment.setTenant( equipmentTO.getTenant() );
        return  equipment;
    }

    @Override
    public EquipmentTO data2io(Equipment equipment) {
        if ( equipment == null )
            return null;
        EquipmentTO io  = new EquipmentTO();
        io.setEquipmentId(  equipment.getId() );
        io.setName( equipment.getName() );
        io.setTenant( equipment.getTenant() );
        io.setCreatedDttm( equipment.getCreatedDttm() );
        io.setUpdateDttm( equipment.getUpdateDttm() );
        io.setVersion( equipment.getVersion() );
        return  io;
    }
}
