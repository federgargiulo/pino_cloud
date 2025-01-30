package it.cogito.equipment.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import it.cogito.equipment.model.Equipment;
import it.cogito.equipment.model.Sensor;
import it.cogito.equipment.repository.EquipmentRepository;
import it.cogito.equipment.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.Optional;

@RestController
public class EquipmentController {
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private SensorRepository sensorRepository;

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
    List<Equipment> all() {

        return equipmentRepository.findAll();

    }

    @GetMapping("/equipments/{id}")
    public Optional<Equipment> getEquipmentById(@PathVariable("id") String id) {
       return equipmentRepository.findById( id );
    }

    @GetMapping("/equipments/{id}/status")
    public String getEquipmentStatus(@PathVariable("id") String id) {
        return "status";
    }

    @PostMapping("/equipments")
    public ResponseEntity<Equipment> createTutorial(@RequestBody Equipment equipment) {
        try {
            equipment = equipmentRepository.save( equipment);

            return new ResponseEntity<>(equipment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/equipments/{id}")
    public ResponseEntity<Equipment> updateTutorial(@PathVariable("id") String id , @RequestBody Equipment equipment ) {
        try {
            equipment.setEquipmentId( id );
            equipment = equipmentRepository.save( equipment );

            return new ResponseEntity<>(equipment, HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/equipments/{id}")
    public ResponseEntity<Equipment> updateTutorial(@PathVariable("id") String id  ) {
        try {
            Equipment equipment = equipmentRepository.getReferenceById( id );
            equipmentRepository.delete( equipment );
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
    public Sensor addSensor( @PathVariable("id") String id , @RequestBody Sensor sensor ) {
          Equipment eq = equipmentRepository.getReferenceById( id );
            return sensor;

    }

    @PostMapping("/equipment/{id}/diagnostics ")
    public String diagnostic( @PathVariable("id") String id , @RequestBody List<Sensor> sensors ) {
       Equipment eq = equipmentRepository.getReferenceById( id );
       return "OK";

    }



}
