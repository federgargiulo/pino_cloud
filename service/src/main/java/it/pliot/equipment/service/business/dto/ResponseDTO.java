package it.pliot.equipment.service.business.dto;

import java.io.Serializable;
import java.util.List;

public class ResponseDTO implements Serializable {

    private String key;
    private List<KeyValueDTO> values;

    // Getters e Setters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<KeyValueDTO> getValues() {
        return values;
    }

    public void setValues(List<KeyValueDTO> values) {
        this.values = values;
    }
}
