package it.pliot.equipment.service.edge;

import it.pliot.equipment.io.EdgeTO;

import it.pliot.equipment.service.business.EdgeServices;
import it.pliot.equipment.service.business.TenantServices;
import jakarta.transaction.Transactional;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Component
@Transactional
@Profile("edge")
public class ServerEdgeServiceImpl implements PliotServerConnection {

    private static final Logger log = LoggerFactory.getLogger(ServerEdgeServiceImpl.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${pliot.edge.tokenuri}")
    private String tokenUri;

    @Value("${pliot.edge.client-id}")
    private String clientId;

    @Value("${pliot.edge.client-secret}")
    private String clientSecret;

    @Value("${pliot.edge.api-url}")
    private String edgeApiUrl;

    @Autowired
    TenantServices tenantService;

    @Autowired
    EdgeServices localEdgeService;


    public EdgeTO registerEdge( EdgeTO requestBody ){
        InizializeEdgeRespTO i = registerOnServer( requestBody );
        EdgeTO to = localEdgeService.save( i.getEdge() );
        log.info( " stored edge locally ");
        tenantService.save( i.getTenant() );
        log.info( " stored tenat locally ");
        return to;
    }

    public InizializeEdgeRespTO registerOnServer( EdgeTO requestBody ) {
        String token = getAccessToken();
        requestBody.setClient( clientId );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EdgeTO> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<InizializeEdgeRespTO> response = restTemplate.postForEntity(
                edgeApiUrl + "/api/edge",
                request,
                InizializeEdgeRespTO.class
        );

        InizializeEdgeRespTO responseBody = response.getBody();
        log.info( "received response " + responseBody );
        return responseBody;
    }

    private String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUri, request, Map.class);

        return (String) response.getBody().get("access_token");
    }
}


