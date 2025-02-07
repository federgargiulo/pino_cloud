package it.pliot.equipment.controller;

import it.pliot.equipment.io.MeasureTO;
import it.pliot.equipment.service.business.MeasureServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeasureController {
    @Autowired
    private MeasureServices measureServices;

    @PostMapping("/measure")
    public ResponseEntity<MeasureTO>  addSensor(@RequestBody MeasureTO in ) {
        try {
            in = measureServices.create(in);
            return new ResponseEntity<>(in, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
