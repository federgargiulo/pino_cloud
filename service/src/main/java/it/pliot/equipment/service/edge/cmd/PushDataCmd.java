package it.pliot.equipment.service.edge.cmd;

import it.pliot.equipment.io.EdgeTO;
import it.pliot.equipment.io.PushDataResultTO;
import it.pliot.equipment.io.PushDataTO;
import it.pliot.equipment.service.edge.InizializeEdgeRespTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Profile("edge")
public class PushDataCmd extends BaseHttpCmd {

    private static final Logger log = LoggerFactory.getLogger(PushDataCmd.class);


    public PushDataResultTO push(PushDataTO requestBody ) {
        String token = getAccessToken();


        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PushDataTO> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<PushDataResultTO> response = getRestTemplate().postForEntity(
                getServerUrl() + "/api/edge",
                request,
                PushDataResultTO.class
        );

        PushDataResultTO responseBody = response.getBody();
        log.info( "received response " + responseBody );
        return responseBody;
    }
}
