package it.pliot.equipment.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SystemConfiguration  extends BaseEntity  {

    @Id
    private String key;

    private String description;

    private String value;

    private boolean isEncripted;

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
