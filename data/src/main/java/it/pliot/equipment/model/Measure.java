package it.pliot.equipment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Entity
public class Measure  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String tenantId;

    private String equipmentId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Measure measure = (Measure) o;
        return Objects.equals(id, measure.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    private String sensorId;

    private String val;

    private Date mesure_dttm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getVal() {
        return val;
    }

    public void setValore(String val) {
        this.val = val;
    }

    public Date getMesure_dttm() {
        return mesure_dttm;
    }

    public void setMesure_dttm(Date mesure_dttm) {
        this.mesure_dttm = mesure_dttm;
    }




}
