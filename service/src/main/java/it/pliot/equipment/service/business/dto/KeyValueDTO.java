package it.pliot.equipment.service.business.dto;

import java.io.Serializable;

public class KeyValueDTO implements Serializable {

    private String name;
    private String value;

    // Getters e Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
