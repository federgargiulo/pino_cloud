package it.pliot.equipment.io;


import it.pliot.equipment.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

public class SystemConfigurationTO extends BaseEntity {


    private String key;

    private String description;

    private String value;

    private boolean isEncripted;

    private Long version;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isEncripted() {
        return isEncripted;
    }

    public void setEncripted(boolean encripted) {
        isEncripted = encripted;
    }
}
