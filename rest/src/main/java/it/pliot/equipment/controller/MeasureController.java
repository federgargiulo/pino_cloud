package it.pliot.equipment.controller;

import it.pliot.equipment.conf.ApiPrefixController;
import it.pliot.equipment.io.PagedResultTO;
import it.pliot.equipment.io.MeasureTO;
import it.pliot.equipment.service.business.MeasureServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@ApiPrefixController
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


    @GetMapping("/measure")
    public ResponseEntity<PagedResultTO>  getMeasure(@RequestParam( name = "signalId" )String signalId ,
                                                     @RequestParam( name ="from"  )  Date from,
                                                     @RequestParam( name ="pageSize" , defaultValue = "100" )  int pageSize,
                                                     @RequestParam( name = "page" , defaultValue = "0") String  page  ) {
        try {

            log.info( " read meature for signal {} gt {} page {} page size {} " , signalId , from , page  , pageSize );
            long un_h_bf = new Date().getTime() - 1 * 60 * 60 *1000;
            if ( from == null )
                from = new Date( un_h_bf );
            PagedResultTO r =  measureServices.search( signalId , from , page , pageSize  );
            return new ResponseEntity<>( r , HttpStatus.OK ) ;


        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
