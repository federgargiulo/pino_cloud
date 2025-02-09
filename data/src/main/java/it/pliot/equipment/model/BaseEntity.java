package it.pliot.equipment.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable,AuditObject {

    private Date createdDttm;

    public Date getCreatedDttm() {
        return createdDttm;
    }

    public void setCreatedDttm(Date createdDttm) {
        this.createdDttm = createdDttm;
    }

    public Date getUpdateDttm() {
        return updateDttm;
    }

    public void setUpdateDttm(Date updateDttm) {
        this.updateDttm = updateDttm;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private Date updateDttm;

    private String version;

}
