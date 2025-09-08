package it.pliot.equipment.service.business.task.impl;

import it.pliot.equipment.io.*;
import it.pliot.equipment.service.business.EquipmentPullerServices;
import it.pliot.equipment.service.business.EquipmentServices;
import it.pliot.equipment.service.ext.DiagnosysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.stream.Collectors;
import java.util.Iterator;

public class AIPuller implements Cmd {

    private static final Logger LOGGER = LoggerFactory.getLogger(AIPuller.class);

    public StringBuffer execute(EquipmentPullerTO eqPuller,
                                EquipmentPullerServices pullerService,
                                ApplicationContext context) {

        StringBuffer buff = new StringBuffer();

        try {
            DiagnosysService dservice = context.getBean(DiagnosysService.class);
            DiagnosysEngineRequestTO dinput = loadAIData(eqPuller, context);
            DiagnosysResultsTO result = dservice.callPythonEngine(dinput);

            buff.append(result.getStatusCode());
            buff.append(" - ");
            buff.append(result.getStatusDescription());
        } catch (AICallException e) {
            LOGGER.error("Errore AI: [{}] {} - {}", e.getType(), e.getMsg(), e.getDesc());
            buff.append("Errore AI: ").append(e.getMsg()).append(" - ").append(e.getDesc());
        } catch (Exception e) {
            LOGGER.error("Errore generico durante l'elaborazione AI", e);
            buff.append("Errore generico: ").append(e.getMessage());
        }

        EquipmentServices equipment = context.getBean(EquipmentServices.class);
        equipment.updateStatus(eqPuller.getEquipmentId(), buff.toString());

        return buff;
    }

    public static String replaceIdInUrl(String url, String newId) {
        if (url != null && url.endsWith("/{id}")) {
            return url.replaceAll("/\\{id}$", "/" + newId);
        }
        return url;
    }

    private static DiagnosysEngineRequestTO loadAIData(EquipmentPullerTO iaPullerTO, ApplicationContext context) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/json;charset=UTF-8");
        headers.set("X-API-KEY", iaPullerTO.getApiKey());

        RestTemplateBuilder restTemplateBuilder = context.getBean(RestTemplateBuilder.class);
        RestTemplate restTemplate = restTemplateBuilder.connectTimeout(Duration.ofSeconds(10)).build();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = replaceIdInUrl(iaPullerTO.getUrl(), iaPullerTO.getPullerId());

        try {
            ResponseEntity<DiagnosysEngineRequestTO> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, DiagnosysEngineRequestTO.class
            );

            DiagnosysEngineRequestTO o = response.getBody();
            LOGGER.info("received response: {}", o);
            return o;
        } catch (HttpClientErrorException e) {
            String body = e.getResponseBodyAsString();
            LOGGER.error("Errore dalla chiamata REST: {}", body);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(body);

            if (root.has("detail") && root.get("detail").isArray()) {
                JsonNode firstDetail = root.get("detail").get(0);
                String msg = firstDetail.has("msg") ? firstDetail.get("msg").asText() : "Errore sconosciuto";
                String type = firstDetail.has("type") ? firstDetail.get("type").asText() : "N/A";
                String desc = "";

                if (firstDetail.has("loc") && firstDetail.get("loc").isArray()) {
                    Iterator<JsonNode> locElements = firstDetail.get("loc").elements();
                    desc = "";
                    while (locElements.hasNext()) {
                        if (!desc.isEmpty()) desc += ".";
                        desc += locElements.next().asText();
                    }
                }

                throw new AICallException(msg, type, desc);
            } else {
                throw new RuntimeException("Errore sconosciuto dal server AI: " + body);
            }
        }
    }

    /**
     * Eccezione custom per errori AI
     */
    public static class AICallException extends Exception {
        private final String msg;
        private final String type;
        private final String desc;

        public AICallException(String msg, String type, String desc) {
            super(msg + " - " + desc);
            this.msg = msg;
            this.type = type;
            this.desc = desc;
        }

        public String getMsg() {
            return msg;
        }

        public String getType() {
            return type;
        }

        public String getDesc() {
            return desc;
        }
    }
}
