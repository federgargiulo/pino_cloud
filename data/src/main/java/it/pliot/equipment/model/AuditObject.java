package it.pliot.equipment.model;

import java.util.Date;

public interface AuditObject {
    public Date getCreatedDttm();
    public void setCreatedDttm(Date createdDttm);
    public Date getUpdateDttm();
    public void setUpdateDttm(Date updateDttm);
}
