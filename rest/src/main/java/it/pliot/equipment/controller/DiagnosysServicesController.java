package it.pliot.equipment.controller;

 
import it.pliot.equipment.conf.ApiPrefixController;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@ApiPrefixController
public class DiagnosysServicesController {


    public static class DiagnosysResultsTO {

        private String statusCode;
        private String statusDescription;

    }

    public static class DiagnosysEngineRequestTO {

        private  String values[];

        public String [] getValues() {
            return values;
        }

        public void setValues(String [] values) {
            this.values = values;
        }
    }

    @PostMapping("/diagnosys/engine")
    public DiagnosysResultsTO exeDiagnosys(@RequestBody
                                            DiagnosysEngineRequestTO dRequest) {

        return new DiagnosysResultsTO();
    }

    private static String BASE_URL = "http://localhost:5000/";

    private final RestTemplate restTemplate = new RestTemplate();

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


    public static void main( String [] args ){
        DiagnosysEngineRequestTO req = new DiagnosysEngineRequestTO();
        String [] values = new String[] {"1" , "3"};

        req.values  = values;

        DiagnosysServicesController service = new DiagnosysServicesController();
        System.out.println( service.exeDiagnosys( req ) );
    }
}
