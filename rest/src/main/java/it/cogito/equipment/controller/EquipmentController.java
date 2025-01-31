package it.cogito.equipment.controller;

import it.cogito.equipment.io.EquipmentIO;
import it.cogito.equipment.io.SensorIO;
import it.cogito.equipment.model.Equipment;
import it.cogito.equipment.model.Sensor;
import it.cogito.equipment.repository.EquipmentRepository;
import it.cogito.equipment.repository.SensorRepository;
import it.cogito.equipment.service.EquipmentService;
import it.cogito.equipment.service.EquipmentServiceImpl;
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

    @GetMapping("/equipments/{id}")
    public  EquipmentIO getEquipmentById(@PathVariable("id") String id) {
        return equipmentService.findById(id);

    }

    @GetMapping("/equipments/{id}/status")
    public String getEquipmentStatus(@PathVariable("id") String id) {
        return "status";
    }

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
