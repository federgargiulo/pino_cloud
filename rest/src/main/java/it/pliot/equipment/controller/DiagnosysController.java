package it.pliot.equipment.controller;

import it.pliot.equipment.conf.ApiPrefixController;
import it.pliot.equipment.io.DiagnosysEngineRequestTO;
import it.pliot.equipment.io.DiagnosysResultsTO;
import it.pliot.equipment.service.ext.DiagnosysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@ApiPrefixController
public class DiagnosysController {


    private static final Logger log = LoggerFactory.getLogger(DiagnosysController.class);

    @Autowired
    private DiagnosysService diagnosysService;

    public DiagnosysController(DiagnosysService diagnosysService) {
        this.diagnosysService = diagnosysService;
    }

    @PostMapping("/diagnoses/engine")
    public DiagnosysResultsTO exeDiagnoses(@RequestBody DiagnosysEngineRequestTO dRequest) {
        log.info( " new diagnosys ");
        return diagnosysService.callPythonEngine(dRequest);
    }

    @GetMapping("/diagnoses/status")
    public String status() {

        return diagnosysService.getStatus();
    }
}