package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.EquipmentPullerTO;
import it.pliot.equipment.model.EquipmentPuller;

public class EquipmentPullerUtils extends BaseConvertUtil<EquipmentPuller, EquipmentPullerTO>{

    private static EquipmentPullerUtils INSTANCE = new EquipmentPullerUtils();

    public static EquipmentPullerUtils instance(){
        return INSTANCE;
      }

    @Override
    public EquipmentPuller io2data(EquipmentPullerTO pullTo ) {
        if ( pullTo == null )
            return null;
        EquipmentPuller puller  = new EquipmentPuller();
        puller.setPullerId( pullTo.getPullerId());
        puller.setEquipmentId( pullTo.getEquipmentId() );
        puller.setIntervalInSec( pullTo.getIntervalInSec() );
        puller.setLastEnd( pullTo.getLastEnd() );
        puller.setLastStart( pullTo.getLastStart() );
        puller.setLastExecution( pullTo.getLastExecution() );
        puller.setNextExecutions( pullTo.getNextExecutions() );
        puller.setLastExecutionReport( pullTo.getLastExecutionReport() );
        puller.setLastEnd( pullTo.getLastEnd() );
        puller.setPullerId( pullTo.getPullerId() );
        puller.setApiKey( pullTo.getApiKey() );
        puller.setUrl( pullTo.getUrl() );
        puller.setTenant( pullTo.getTenant() );
        return  puller;
    }

    @Override
    public EquipmentPullerTO data2io(EquipmentPuller puller ) {
        if ( puller == null )
            return null;
        EquipmentPullerTO pullerTo  = new EquipmentPullerTO();
        pullerTo.setPullerId( puller.getPullerId() );
        pullerTo.setEquipmentId( puller.getEquipmentId() );
        pullerTo.setIntervalInSec( puller.getIntervalInSec() );
        pullerTo.setLastEnd( puller.getLastEnd() );
        pullerTo.setLastStart( puller.getLastStart() );
        pullerTo.setLastExecution( puller.getLastExecution() );
        pullerTo.setLastExecutionReport( puller.getLastExecutionReport() );
        pullerTo.setLastEnd( puller.getLastEnd() );
        pullerTo.setNextExecutions( puller.getNextExecutions() );
        pullerTo.setPullerId( puller.getPullerId() );
        pullerTo.setApiKey( puller.getApiKey());
        pullerTo.setUrl( puller.getUrl() );
        pullerTo.setTenant( puller.getTenant() );
        return  pullerTo;
    }
}
