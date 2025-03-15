package it.pliot.equipment.io;


import it.pliot.equipment.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

public class SystemConfigurationTO extends BaseTO {


    private String configurationKey;

    private String description;

    private String configurationValue;

    private boolean isEncripted;

    private Long version;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getConfigurationKey() {
        return configurationKey;
    }

    public void setConfigurationKey(String configurationKey) {
        this.configurationKey = configurationKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConfigurationValue() {
        return configurationValue;
    }

    public void setConfigurationValue(String configurationValue) {
        this.configurationValue = configurationValue;
    }

    public boolean isEncripted() {
        return isEncripted;
    }

    public void setEncripted(boolean encripted) {
        isEncripted = encripted;
    }

    private Date createdDttm;

    public Date getCreatedDttm() {
        return createdDttm;
    }

    public void setCreatedDttm(Date createdDttm) {
        this.createdDttm = createdDttm;
    }

   }
