package it.pliot.equipment.io;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class DiagnosysEngineRequestTO implements Serializable{
    private List<Sample> samples;

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }
}
