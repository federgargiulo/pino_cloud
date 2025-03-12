package it.pliot.equipment.controller;

import it.pliot.equipment.conf.ApiPrefixController;
import it.pliot.equipment.conf.InitDb;
import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.io.SignalTO;
import it.pliot.equipment.security.UserContext;
import it.pliot.equipment.service.business.EquipmentServices;
import it.pliot.equipment.service.business.SignalServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@ApiPrefixController
public class EquipmentController {

    private static final Logger log = LoggerFactory.getLogger(InitDb.class);

    @Autowired
    private EquipmentServices equipmentService;

    @Autowired
    private SignalServices signalServices;




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


}
