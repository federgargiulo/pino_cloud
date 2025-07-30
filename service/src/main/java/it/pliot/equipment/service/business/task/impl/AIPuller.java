package it.pliot.equipment.service.business.task.impl;

import it.pliot.equipment.io.*;
import it.pliot.equipment.service.business.EquipmentPullerServices;
import it.pliot.equipment.service.business.dto.ResponseDTO;
import it.pliot.equipment.service.ext.DiagnosysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

public class AIPuller implements  Cmd {

    private static final Logger LOGGER = LoggerFactory.getLogger(AIPuller.class);

    public StringBuffer execute(EquipmentPullerTO eqPuller,
                                EquipmentPullerServices pullerService,
                                ApplicationContext context) {

        ResponseDTO response = null;
        StringBuffer buff = new StringBuffer();


        DiagnosysService dservice = context.getBean(DiagnosysService.class);
        DiagnosysEngineRequestTO dinput = loadAIData(eqPuller, context);
        DiagnosysResultsTO result = dservice.callPythonEngine(dinput);

        buff.append(result.getStatusCode());
        buff.append(" - ");
        buff.append(result.getStatusDescription());
        return buff;

    }


    public static String replaceIdInUrl(String url, String newId) {
        if (url != null && url.endsWith("/{id}")) {
            return url.replaceAll("/\\{id}$", "/" + newId);
        }
        return url;
    }


    private static DiagnosysEngineRequestTO loadAIData(EquipmentPullerTO iaPullerTO, ApplicationContext context) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/json;charset=UTF-8");
        headers.set("X-API-KEY", iaPullerTO.getApiKey());

        RestTemplateBuilder restTemplateBuilder = (RestTemplateBuilder) context.getBean(RestTemplateBuilder.class);
        RestTemplate restTemplate = restTemplateBuilder.connectTimeout(Duration.ofSeconds(10))
                .build();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<DiagnosysEngineRequestTO> response = restTemplate.exchange(
                replaceIdInUrl(iaPullerTO.getUrl(), iaPullerTO.getPullerId()), HttpMethod.GET, entity, DiagnosysEngineRequestTO.class
        );

        DiagnosysEngineRequestTO o = response.getBody();
        LOGGER.info(" received response " + o);
        return o;

    }
}




