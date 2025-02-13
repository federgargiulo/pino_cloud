package it.pliot.equipment.controller;

import it.pliot.equipment.conf.InitDb;
import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.io.SensorTO;
import it.pliot.equipment.model.Sensor;
import it.pliot.equipment.service.business.EquipmentServices;
import it.pliot.equipment.service.business.SensorServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EquipmentController {

    private static final Logger log = LoggerFactory.getLogger(InitDb.class);

    @Autowired
    private EquipmentServices equipmentService;

    @Autowired
    private SensorServices sensorServices;




    @GetMapping("/equipments")
    public List<EquipmentTO> all() {
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

    @DeleteMapping("/equipments/{id}")
    public ResponseEntity<EquipmentTO> updateEquipment(@PathVariable("id") String id  ) {
        try {
            EquipmentTO equipment = equipmentService.findById( id );
            return new ResponseEntity<>(equipment, HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/equipments/{id}/sensors/{idSensor}")
    public SensorTO getSensorById(@PathVariable("idSensor") String id) {
        return sensorServices.findById( id );
    }

    @PostMapping("/equipment/{id}/sensor")
    public SensorTO addSensor(@PathVariable("id") String id , @RequestBody SensorTO sensor ) {
        sensor.setEquipmentId( id );
        return sensorServices.save( sensor );

    }

    @PostMapping("/equipment/{id}/diagnostics ")
    public String diagnostic( @PathVariable("id") String id , @RequestBody List<Sensor> sensors ) {
       EquipmentTO eq = equipmentService.findById( id );
       return "OK";
    }

}
