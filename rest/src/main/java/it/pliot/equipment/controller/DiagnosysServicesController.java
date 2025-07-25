//package it.pliot.equipment.controller;

 
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;

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

    //@PostMapping("/diagnosys/engine")
    public DiagnosysResultsTO exeDiagnosys(//@RequestBody
                                            DiagnosysEngineRequestTO dRequest) {

        return new DiagnosysResultsTO();
    }


    public static void main( String [] args ){
        DiagnosysEngineRequestTO req = new DiagnosysEngineRequestTO();
        String [] values = new String[] {"1" , "3"};

        req.values  = values;

        DiagnosysServicesController service = new DiagnosysServicesController();
        System.out.println( service.exeDiagnosys( req ) );
    }
}
