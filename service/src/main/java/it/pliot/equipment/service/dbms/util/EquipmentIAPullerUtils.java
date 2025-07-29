package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.EquipmentIAPullerTO;
import it.pliot.equipment.io.EquipmentPullerTO;
import it.pliot.equipment.model.EquipmentIAPuller;
import it.pliot.equipment.model.EquipmentPuller;

public class EquipmentIAPullerUtils extends BaseConvertUtil<EquipmentIAPuller, EquipmentIAPullerTO>{

    private static EquipmentIAPullerUtils INSTANCE = new EquipmentIAPullerUtils();

    public static EquipmentIAPullerUtils instance(){
        return INSTANCE;
      }

    @Override
    public EquipmentIAPuller io2data(EquipmentIAPullerTO iaPullerTO ) {
        if ( iaPullerTO == null )
            return null;
        EquipmentIAPuller iaPuller  = new EquipmentIAPuller();
        iaPuller.setIaPullerId( iaPullerTO.getIaPullerId());
        iaPuller.setEquipmentId( iaPullerTO.getEquipmentId() );
        iaPuller.setIntervalInSec( iaPullerTO.getIntervalInSec() );
        iaPuller.setLastEnd( iaPullerTO.getLastEnd() );
        iaPuller.setLastStart( iaPullerTO.getLastStart() );
        iaPuller.setLastExecution( iaPullerTO.getLastExecution() );
        iaPuller.setNextExecutions( iaPullerTO.getNextExecutions() );
        iaPuller.setLastExecutionReport( iaPullerTO.getLastExecutionReport() );
        iaPuller.setLastEnd( iaPullerTO.getLastEnd() );
        iaPuller.setApiKey( iaPullerTO.getApiKey() );
        iaPuller.setUrl( iaPullerTO.getUrl() );
        iaPuller.setTenant( iaPullerTO.getTenant() );
        return  iaPuller;
    }

    @Override
    public EquipmentIAPullerTO data2io(EquipmentIAPuller iaPuller) {
        if ( iaPuller == null )
            return null;
        EquipmentIAPullerTO iaPullerTO  = new EquipmentIAPullerTO();
        iaPullerTO.setIaPullerId( iaPuller.getIaPullerId() );
        iaPullerTO.setEquipmentId( iaPuller.getEquipmentId() );
        iaPullerTO.setIntervalInSec( iaPuller.getIntervalInSec() );
        iaPullerTO.setLastEnd( iaPuller.getLastEnd() );
        iaPullerTO.setLastStart( iaPuller.getLastStart() );
        iaPullerTO.setLastExecution( iaPuller.getLastExecution() );
        iaPullerTO.setLastExecutionReport( iaPuller.getLastExecutionReport() );
        iaPullerTO.setLastEnd( iaPuller.getLastEnd() );
        iaPullerTO.setNextExecutions( iaPuller.getNextExecutions() );

        iaPullerTO.setApiKey( iaPuller.getApiKey());
        iaPullerTO.setUrl( iaPuller.getUrl() );
        iaPullerTO.setTenant( iaPuller.getTenant() );
        return  iaPullerTO;
    }
}
