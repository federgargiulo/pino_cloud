package it.pliot.equipment.controller;

import it.pliot.equipment.io.EquipmentIO;
import it.pliot.equipment.io.SensorIO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.model.Sensor;
import it.pliot.equipment.repository.EquipmentRepository;
import it.pliot.equipment.repository.SensorRepository;
import it.pliot.equipment.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EquipmentController {
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private EquipmentService equipmentService;

    public EquipmentService getEquipmentService() {
        return equipmentService;
    }

    public void setEquipmentService(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    public EquipmentRepository getEquipmentRepository() {
        return equipmentRepository;
    }

    public void setEquipmentRepository(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public void setSensorRepository(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public SensorRepository getSensorRepository() {
        return sensorRepository;
    }



    @GetMapping("/equipments")
    public List<EquipmentIO> all() {
        return equipmentService.all();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/equipments/{id}")
    public  EquipmentIO getEquipmentById(@PathVariable("id") String id) {
        return equipmentService.findById(id);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/equipments/{id}/status")
    public String getEquipmentStatus(@PathVariable("id") String id) {
        return "status";
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/equipments")
    public ResponseEntity<EquipmentIO> createTutorial(@RequestBody EquipmentIO equipment) {
        try {
            equipment = equipmentService.save( equipment);
            return new ResponseEntity<>(equipment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/equipments/{id}")
    public ResponseEntity<EquipmentIO> updateTutorial(@PathVariable("id") String id , @RequestBody EquipmentIO equipment ) {
        try {
            equipment.setEquipmentId( id );
            equipment = equipmentService.save( equipment );

            return new ResponseEntity<>(equipment, HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/equipments/{id}")
    public ResponseEntity<EquipmentIO> updateEquipment(@PathVariable("id") String id  ) {
        try {
            EquipmentIO equipment = equipmentService.findById( id );
            return new ResponseEntity<>(equipment, HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/equipments/{id}/sensors/{idSensor}")
    public Optional<Sensor> getSensorById(@PathVariable("idSensor") String id) {
        return sensorRepository.findById( id );
    }

    @PostMapping("/equipment/{id}/sensor")
    public SensorIO addSensor( @PathVariable("id") String id , @RequestBody SensorIO sensor ) {
        sensor.setEquipmentId( id );
        return equipmentService.addSensor( sensor );

    }

    @PostMapping("/equipment/{id}/diagnostics ")
    public String diagnostic( @PathVariable("id") String id , @RequestBody List<Sensor> sensors ) {
       Equipment eq = equipmentRepository.getReferenceById( id );
       return "OK";
    }



}
