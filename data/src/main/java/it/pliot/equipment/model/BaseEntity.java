package it.pliot.equipment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Version;

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


    private Date updateDttm;
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version;

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
