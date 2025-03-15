package it.pliot.equipment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Version;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseEntity implements Serializable,AuditObject {

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

    public abstract void setTenant( String tenant );

    private Date updateDttm;
    @Version
    @Column(name = "optlock", columnDefinition = "long DEFAULT 0", nullable = false)
    private Long version;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
