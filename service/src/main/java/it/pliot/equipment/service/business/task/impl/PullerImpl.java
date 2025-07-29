package it.pliot.equipment.service.business.task.impl;

import it.pliot.equipment.Const;
import it.pliot.equipment.io.EquipmentPullerTO;
import it.pliot.equipment.service.business.EquipmentPullerServices;
import it.pliot.equipment.service.business.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class PullerImpl {



    private static final Logger LOGGER = LoggerFactory.getLogger(PullerImpl.class);



    public void executeRetrieve( EquipmentPullerTO eqPuller ,
                          EquipmentPullerServices pullerService ,
                          ApplicationContext context ){

        ResponseDTO response = null;
        StringBuffer buff = new StringBuffer();
        try {
            // manage the lock here
            pullerService.startPull( eqPuller.getPullerId() );

           Cmd c = getCommandObject( eqPuller );
           StringBuffer b = c.execute( eqPuller , pullerService , context );
           buff.append( b );

        } catch (Exception e) {
            buff.append( e.getMessage() );
        }finally {
            pullerService.endPull(eqPuller.getPullerId(),  buff.toString() );
        }

    }

    private MeasurePuller mpuller = new MeasurePuller();
    private AIPuller aiPuller = new AIPuller();


    private Cmd getCommandObject(EquipmentPullerTO eqPuller) {
        if ( Const.AI_PULLER.equals( eqPuller.getPullType() ) )
            return aiPuller;
        return mpuller;
    }


}


