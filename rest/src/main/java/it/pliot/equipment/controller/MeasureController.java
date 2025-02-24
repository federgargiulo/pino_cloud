package it.pliot.equipment.controller;

import it.pliot.equipment.io.MeasureTO;
import it.pliot.equipment.service.business.MeasureServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MeasureController {

    private static final Logger log = LoggerFactory.getLogger(MeasureController.class);

    @Autowired
    private MeasureServices measureServices;

    @PostMapping("/measure")
    public ResponseEntity<MeasureTO>  addMeasure(@RequestBody MeasureTO in ) {
        try {
            in = measureServices.create(in);
            return new ResponseEntity<>(in, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping("/bulkmeasure")
    public ResponseEntity  addMeasure(@RequestBody(required = true) MeasureTO ... in ) {
        try {
            log.info( "received bulkmeasure " + in );
            List<MeasureTO> toINsert =Arrays.asList( in );
            measureServices.saveAll( toINsert  );
            return new ResponseEntity<>( HttpStatus.OK);
        }catch (Exception e) {
            log.error( e.getMessage() );
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
