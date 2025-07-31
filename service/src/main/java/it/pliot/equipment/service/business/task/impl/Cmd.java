package it.pliot.equipment.service.business.task.impl;

import it.pliot.equipment.io.EquipmentPullerTO;
import it.pliot.equipment.service.business.EquipmentPullerServices;
import org.springframework.context.ApplicationContext;

public interface Cmd {

    public StringBuffer execute( EquipmentPullerTO eqPuller ,
                                 EquipmentPullerServices pullerService ,
                                 ApplicationContext context ) ;
}
