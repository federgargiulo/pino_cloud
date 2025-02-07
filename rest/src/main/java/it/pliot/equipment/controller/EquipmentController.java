package it.pliot.equipment.controller;

import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.io.SensorTO;
import it.pliot.equipment.model.Sensor;
import it.pliot.equipment.service.business.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EquipmentController {
    @Autowired
    private CreateEquipmentService createEquipmentService;

    @Autowired
    private UpdateEquipmentService updateEquipmentService;

    @Autowired
    private FindEquipmentByIdService findEquipmentService;

    @Autowired
    private ReadEquipmentsService readEquipmentService;


    @Autowired
    private AddSensorService addSensorService;

    @Autowired
    private FindSensorByIdService findSensorByIdService;

    @Autowired
    private ReadReferenceByIdService readReferenceByIdService;


    @GetMapping("/equipments")
    public List<EquipmentTO> all() {
        return readEquipmentService.all();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/equipments/{id}")
    public EquipmentTO getEquipmentById(@PathVariable("id") String id) {
        return findEquipmentService.findById(id);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/equipments/{id}/status")
    public String getEquipmentStatus(@PathVariable("id") String id) {
        return "status";
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/equipments")
    public ResponseEntity<EquipmentTO> createTutorial(@RequestBody EquipmentTO equipment) {
        try {
            equipment = updateEquipmentService.save( equipment);
            return new ResponseEntity<>(equipment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/equipments/{id}")
    public ResponseEntity<EquipmentTO> updateTutorial(@PathVariable("id") String id , @RequestBody EquipmentTO equipment ) {
        try {
            equipment.setEquipmentId( id );
            equipment = updateEquipmentService.save( equipment );

            return new ResponseEntity<>(equipment, HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/equipments/{id}")
    public ResponseEntity<EquipmentTO> updateEquipment(@PathVariable("id") String id  ) {
        try {
            EquipmentTO equipment = findEquipmentService.findById( id );
            return new ResponseEntity<>(equipment, HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/equipments/{id}/sensors/{idSensor}")
    public SensorTO getSensorById(@PathVariable("idSensor") String id) {
        return findSensorByIdService.findById( id );
    }

    @PostMapping("/equipment/{id}/sensor")
    public SensorTO addSensor(@PathVariable("id") String id , @RequestBody SensorTO sensor ) {
        sensor.setEquipmentId( id );
        return addSensorService.addSensor( sensor );

    }

    @PostMapping("/equipment/{id}/diagnostics ")
    public String diagnostic( @PathVariable("id") String id , @RequestBody List<Sensor> sensors ) {
       EquipmentTO eq = readReferenceByIdService.getReferenceById( id );
       return "OK";
    }

}
