package it.pliot.equipment.controller;

import it.pliot.equipment.conf.ApiPrefixController;
import it.pliot.equipment.conf.InitDb;
import it.pliot.equipment.io.EquipmentPullerTO;
import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.io.SignalTO;
import it.pliot.equipment.security.UserContext;
import it.pliot.equipment.service.business.EquipmentPullerServices;
import it.pliot.equipment.service.business.EquipmentServices;
import it.pliot.equipment.service.business.SignalServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@ApiPrefixController
public class EquipmentController {

    private static final Logger log = LoggerFactory.getLogger(InitDb.class);

    @Autowired
    private EquipmentServices equipmentService;

    @Autowired
    private SignalServices signalServices;


    @Autowired
    private EquipmentPullerServices equipmentPullerServices;



    @GetMapping("/equipments")
    public List<EquipmentTO> all( ) {

        return equipmentService.findAll();
    }


    @GetMapping("/equipments/{id}")
    public EquipmentTO getEquipmentById(@PathVariable("id") String id) {
        return equipmentService.findById(id);

    }


    @GetMapping("/equipments/{id}/status")
    public String getEquipmentStatus(@PathVariable("id") String id) {
        return "status";
    }


    @PostMapping("/equipments")
    public ResponseEntity<EquipmentTO> createEquipment(@RequestBody EquipmentTO equipment) {
        try {
            log.info( " create a new Equipment"  + equipment );
            equipment.setTenant(UserContext.currentUser().getTenantId());

            equipment = equipmentService.create( equipment);
            return new ResponseEntity<>(equipment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/equipments/{id}")
    public ResponseEntity<EquipmentTO> updateTutorial(@PathVariable("id") String id , @RequestBody EquipmentTO equipment ) {
        try {
            equipment.setEquipmentId( id );
            equipment = equipmentService.save( equipment );

            return new ResponseEntity<>(equipment, HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/equipments/{id}")
    public ResponseEntity<EquipmentTO> updateEquipment(@PathVariable("id") String id , @RequestBody EquipmentTO equipment ) {
        try {
            equipment.setEquipmentId( id );
            equipment = equipmentService.save( equipment );
            return new ResponseEntity<>(equipment, HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/equipments/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable("id") String id  ) {
            equipmentService.delete( id );
            return ResponseEntity.noContent().build();
    }


    @GetMapping("/equipments/{id}/signals/{idSignal}")
    public SignalTO getSignalById(@PathVariable("idSignal") String id) {
        return signalServices.findById( id );
    }

    @GetMapping("/equipments/{equipmentId}/signals")
    public ResponseEntity<List<SignalTO>> getSignals(@PathVariable ("equipmentId") String equipmentId) {
        List<SignalTO> signals = signalServices.getSignalsByEquipmentId(equipmentId);
        return ResponseEntity.ok(signals);
    }

    @PostMapping("/equipments/{id}/signals")
    public SignalTO addSignal(@PathVariable("id") String id , @RequestBody SignalTO signalTO ) {

        signalTO.setEquipmentId( id );
        return signalServices.create( signalTO );

    }

    @PostMapping("/equipments/{id}/diagnostics ")
    public String diagnostic( @PathVariable("id") String id , @RequestBody List<SignalTO> signals ) {
       EquipmentTO eq = equipmentService.findById( id );
       return "OK";
    }

    @DeleteMapping("/equipments/{id}/signals/{idSignal}")
    public ResponseEntity<Void> deleteSignalById(@PathVariable("idSignal") String idSignal  ) {
        signalServices.delete( idSignal );
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/equipments/{id}/signals/{idSignal}")
    public ResponseEntity<SignalTO> updateSignal(@PathVariable("idSignal") String idSignal , @RequestBody SignalTO signalTO) {
        try {
            signalTO.setSignalId( idSignal );
            signalTO = signalServices.save( signalTO );
            return new ResponseEntity<>(signalTO, HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/equipments/{equipmentId}/pullers")
    public ResponseEntity<List<EquipmentPullerTO>> getEquipmentPullers(@PathVariable ("equipmentId") String equipmentId) {
        List<EquipmentPullerTO> signals = equipmentPullerServices.puller4Equipment(equipmentId);
        return ResponseEntity.ok(signals);
    }

    @GetMapping("/equipments/{id}/pullers/{idPuller}")
    public EquipmentPullerTO getEquipmentPullerById(@PathVariable("idPuller") String id) {
        return equipmentPullerServices.findById( id );
    }

    @DeleteMapping("/equipments/{equipmentId}/pullers/{idPuller}")
    public ResponseEntity<Void> deletePullerById(@PathVariable("equipmentId") String equipmentId, @PathVariable("idPuller") String idPuller  ) {
        equipmentPullerServices.delete( idPuller );
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/equipments/{equipmentId}/pullers")
    public EquipmentPullerTO createEquipmentPuller(@PathVariable("equipmentId") String equipmentId , @RequestBody EquipmentPullerTO equipmentPullerTO ) {
        equipmentPullerTO.setEquipmentId(equipmentId);
        Date nextEsecution=addSecondsToNow(equipmentPullerTO.getIntervalInSec());
        equipmentPullerTO.setLastEnd(nextEsecution);
        equipmentPullerTO.setNextExecutions(nextEsecution);
        return equipmentPullerServices.create( equipmentPullerTO );

    }

    private Date addSecondsToNow(Integer seconds) {
        if (seconds == null) {
            throw new IllegalArgumentException("Il parametro deve essere un numero intero valido.");
        }
        Instant now = Instant.now();
        Instant futureInstant = now.plus(seconds, ChronoUnit.SECONDS);
        return Date.from(futureInstant);
    }

    @PatchMapping("/equipments/{equipmentId}/pullers/{pullerId}")
    public ResponseEntity<EquipmentPullerTO> updatePuller(@PathVariable("equipmentId") String equipmentId , @PathVariable("pullerId") String pullerId , @RequestBody EquipmentPullerTO equipmentPullerTO) {
        try {
            equipmentPullerTO.setEquipmentId(equipmentId);
            equipmentPullerTO.setPullerId( pullerId );
            equipmentPullerTO = equipmentPullerServices.save( equipmentPullerTO );
            return new ResponseEntity<>(equipmentPullerTO, HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
