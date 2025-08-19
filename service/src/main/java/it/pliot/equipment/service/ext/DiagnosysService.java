package it.pliot.equipment.service.ext;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.pliot.equipment.io.DiagnosysEngineRequestTO;
import it.pliot.equipment.io.DiagnosysResultsTO;
import it.pliot.equipment.io.Sample;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class DiagnosysService {

    private static final String BASE_URL = "http://localhost:5000/";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public DiagnosysService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public DiagnosysResultsTO callPythonEngine(DiagnosysEngineRequestTO request) {
        try {
            String pythonApiUrl = BASE_URL + "predict";

            List<Sample> samples = request.getSamples();
            Map<String, Object> body = Map.of("samples", samples);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(body), headers);

            ResponseEntity<DiagnosysResultsTO> response = restTemplate.exchange(
                    pythonApiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    DiagnosysResultsTO.class
            );

            return response.getBody();
        } catch (Exception e) {
            DiagnosysResultsTO error = new DiagnosysResultsTO();
            error.setStatusCode("ERROR");
            error.setStatusDescription("Python API error: " + e.getMessage());
            return error;
        }
    }

    public String getStatus() {
        try {
            String url = BASE_URL + "status";
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            return "Error on API Call: " + e.getMessage();
        }
    }
}
