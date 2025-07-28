package it.pliot.equipment.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.pliot.equipment.conf.ApiPrefixController;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@ApiPrefixController
public class DiagnosysServicesController {

    private static final String BASE_URL = "http://localhost:5000/";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Output del servizio Python
    public static class DiagnosysResultsTO {
        private String statusCode;
        private String statusDescription;

        public String getStatusCode() { return statusCode; }
        public void setStatusCode(String statusCode) { this.statusCode = statusCode; }

        public String getStatusDescription() { return statusDescription; }
        public void setStatusDescription(String statusDescription) { this.statusDescription = statusDescription; }

        @Override
        public String toString() {
            return "DiagnosysResultsTO{" +
                    "statusCode='" + statusCode + '\'' +
                    ", statusDescription='" + statusDescription + '\'' +
                    '}';
        }
    }

    // Input da frontend
    public static class DiagnosysEngineRequestTO {
        private String[] values;

        public String[] getValues() {
            return values;
        }

        public void setValues(String[] values) {
            this.values = values;
        }
    }

    // Struttura dei dati per FastAPI
    public static class Sample {
        public String timestamp;
        @JsonProperty("voltage_V_L1") public double voltageVL1;
        @JsonProperty("voltage_V_L2") public double voltageVL2;
        @JsonProperty("voltage_V_L3") public double voltageVL3;
        @JsonProperty("current_A_L1") public double currentAL1;
        @JsonProperty("current_A_L2") public double currentAL2;
        @JsonProperty("current_A_L3") public double currentAL3;
        @JsonProperty("vibration_rms_mm_s") public double vibration;
        @JsonProperty("stator_temp_C") public double statorTemp;
        @JsonProperty("speed_rpm") public double speed;

        public Sample(String[] values) {
            this.timestamp = new Date().toString();
            this.voltageVL1 = Double.parseDouble(values[0]);
            this.voltageVL2 = Double.parseDouble(values[1]);
            this.voltageVL3 = Double.parseDouble(values[2]);
            this.currentAL1 = Double.parseDouble(values[3]);
            this.currentAL2 = Double.parseDouble(values[4]);
            this.currentAL3 = Double.parseDouble(values[5]);
            this.vibration = Double.parseDouble(values[6]);
            this.statorTemp = Double.parseDouble(values[7]);
            this.speed = Double.parseDouble(values[8]);
        }
    }

    @PostMapping("/diagnosys/engine")
    public DiagnosysResultsTO exeDiagnosys(@RequestBody DiagnosysEngineRequestTO dRequest) {
        try {
            String pythonApiUrl = BASE_URL + "predict";

            // Costruisci il JSON come richiesto dall'API Python
            List<Sample> samples = List.of(new Sample(dRequest.getValues()));
            Map<String, Object> body = new HashMap<>();
            body.put("samples", samples);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(
                    objectMapper.writeValueAsString(body), headers
            );

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

    @GetMapping("/diagnosys/status")
    public String status() {
        try {
            String pythonApiUrl = BASE_URL + "status";
            ResponseEntity<String> response = restTemplate.getForEntity(pythonApiUrl, String.class);
            return response.getBody();
        } catch (Exception e) {
            return "Error on API Call: " + e.getMessage();
        }
    }

    // test locale
    public static void main(String[] args) {
        DiagnosysEngineRequestTO req = new DiagnosysEngineRequestTO();
        req.setValues(new String[]{"230", "231", "229", "10", "10", "11", "1.2", "65", "1480"});

        DiagnosysServicesController service = new DiagnosysServicesController();
        System.out.println(service.exeDiagnosys(req));
    }
}
