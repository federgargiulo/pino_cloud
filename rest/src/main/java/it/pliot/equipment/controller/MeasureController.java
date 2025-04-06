package it.pliot.equipment.controller;

import it.pliot.equipment.conf.ApiPrefixController;
import it.pliot.equipment.io.AggregateResultTO;
import it.pliot.equipment.io.Aggregation;
import it.pliot.equipment.io.PagedResultTO;
import it.pliot.equipment.io.MeasureTO;
import it.pliot.equipment.service.business.MeasureServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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



    @GetMapping("/aggregation")
    public List<AggregateResultTO> getAggregation(

                @RequestParam String signalId,
                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME ) LocalDateTime startDate,
                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                @RequestParam(required = false) String aggregation

        ) {
        // Data di oggi
        if ( endDate == null )
            endDate = LocalDateTime.now();
        if ( startDate == null )
            startDate = endDate.minusMonths( 1 );

        // Conversione in java.util.Date
        Date end = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());
        Date start = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
        Aggregation agg = Aggregation.DAY;
        try {
            if  (aggregation != null )
                agg = Aggregation.valueOf(aggregation) ;
        }catch (Exception e ){
            log.error( " error " + e.getMessage() );
        }
        return measureServices.getAggregatedData( agg.name() , signalId , start , end );
    }

}
