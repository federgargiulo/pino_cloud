package it.pliot.equipment.service.edge.cmd;

import it.pliot.equipment.io.EdgeTO;
import it.pliot.equipment.service.edge.InizializeEdgeRespTO;
import it.pliot.equipment.service.edge.ServerEdgeServiceImpl;
import it.pliot.equipment.service.edge.TokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Profile("edge")
public class RegisterCmd extends BaseHttpCmd {

    private static final Logger log = LoggerFactory.getLogger(RegisterCmd.class);



    public InizializeEdgeRespTO execute( EdgeTO requestBody ) {
        String token = getAccessToken();
        requestBody.setClient( getClientId() );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EdgeTO> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<InizializeEdgeRespTO> response = getRestTemplate().postForEntity(
                getServerUrl() + "/api/edge",
                request,
                InizializeEdgeRespTO.class
        );

        InizializeEdgeRespTO responseBody = response.getBody();
        log.info( "received response " + responseBody );
        return responseBody;
    }



}
