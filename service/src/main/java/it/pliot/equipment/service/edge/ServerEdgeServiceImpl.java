package it.pliot.equipment.service.edge;

import it.pliot.equipment.io.*;

import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.service.business.EdgeServices;
import it.pliot.equipment.service.business.EquipmentServices;
import it.pliot.equipment.service.business.SyncCheckpointsServices;
import it.pliot.equipment.service.business.TenantServices;
import it.pliot.equipment.service.edge.cmd.PushDataCmd;
import it.pliot.equipment.service.edge.cmd.RegisterCmd;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Component
@Transactional
@Profile("edge")
public class ServerEdgeServiceImpl implements PliotServerConnection {

    private static final Logger log = LoggerFactory.getLogger(ServerEdgeServiceImpl.class);


    private static long MILLISEC_IN_A_MINUTE = 60 * 1000;

    private static long YEAR_AGO_MILLISEC    = 355 * 24 * 60 * 60 * 1000;

    private Date STARTING_DTTM = null;
    public ServerEdgeServiceImpl(){
        STARTING_DTTM = getXMinutesAgo( 355 * 24 * 60 );
    }
    private Date getXMinutesAgo( int x ) {

        Date d = new Date();
        return new Date( ( d.getTime() -  x * MILLISEC_IN_A_MINUTE ) );
    }


    private Date get10MinutesAgo(){
        return getXMinutesAgo( 10 );
    }

    @Autowired
    SyncCheckpointsServices syncCheckpointsServices;

    @Autowired
    TenantServices tenantService;

    @Autowired
    EdgeServices localEdgeService;

    @Autowired
    PushDataCmd pushCmd;

    @Autowired
    RegisterCmd registerCmd;

    @Autowired
    EquipmentServices equipmentServices;

    public EdgeTO registerEdge( EdgeTO requestBody ){
        InizializeEdgeRespTO i = registerCmd.execute( requestBody );

        EdgeTO to = localEdgeService.save( i.getEdge() );
        log.info( " stored edge locally ");
        tenantService.save( i.getTenant() );
        log.info( " stored tenat locally ");
        return to;
    }

    @Override
    public PushDataResultTO pushData() {

        PushDataResultTO result = null;
        log.info(" Aggregate 10 minutes " + this);
        String equipmentKey = Equipment.class.getName();
        Date to = getXMinutesAgo(10);
        Date from = null;
        PushDataTO redData = new PushDataTO();
        boolean hastosend = false;

        SyncCheckpointsTO cp = syncCheckpointsServices.findById(equipmentKey);
        if (cp == null) {
            from = STARTING_DTTM;
        }
        List<EquipmentTO> equipments =
                equipmentServices.findUpdatedEquipmentInTheInterval(from, to);
        if (equipments != null && equipments.size() > 0) {
            hastosend = true;
            redData.setEquipments(equipments);
        }

        if (hastosend) {
            result = pushCmd.push(redData);

        }else {
            result = new PushDataResultTO();
        }
        syncCheckpointsServices.updateSynckpointsService(equipmentKey, to);
        return result;

    }

}


