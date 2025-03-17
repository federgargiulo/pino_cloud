package it.pliot.equipment.service.business.task.impl;

import it.pliot.equipment.io.EquipmentPullerTO;
import it.pliot.equipment.service.business.EquipmentPullerServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

public class HttpLoaderImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpLoaderImpl.class);



    public void executeRetrieve( EquipmentPullerTO eqPuller ,
                          EquipmentPullerServices pullerService ,
                          ApplicationContext context ){

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/json;charset=UTF-8");
        RestTemplateBuilder restTemplateBuilder = ( RestTemplateBuilder ) context.getBean(RestTemplateBuilder.class);
        RestTemplate restTemplate = restTemplateBuilder.connectTimeout( Duration.ofSeconds( 10 ) )
                .build();
        Object o = restTemplate.getForObject(
                eqPuller.getUrl() ,
                ResponseEntity.class);

        LOGGER.info( " received response " + o );



    }



}


