package it.pliot.equipment.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SystemConfiguration  extends BaseEntity  {

    @Id
    private String configurationKey;

    private String description;

    private String configurationValue;


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
}
