package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.model.Equipment;

public class EquipmentUtils extends BaseConvertUtil<Equipment, EquipmentTO>{

    private static EquipmentUtils INSTANCE = new EquipmentUtils();

    public static EquipmentUtils instance(){
        return INSTANCE;
      }

    @Override
    public Equipment io2data(EquipmentTO equipmentTO) {
        if ( equipmentTO == null )
            return null;
        Equipment equipment  = new Equipment();
        equipment.setId(  equipmentTO.getEquipmentId() );
        equipment.setName( equipmentTO.getName() );
        equipment.setVersion( ConvertUtils.string2long( equipmentTO.getVersion() ) );
        equipment.setTenant( equipmentTO.getTenant() );
        equipment.setStatus( equipmentTO.getStatus() );
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
        io.setStatus( equipment.getStatus() );
        if (null != equipment.getVersion() && !"".equals(equipment.getVersion()))
            io.setVersion( Long.toString( equipment.getVersion() ) );
        return  io;
    }
}
