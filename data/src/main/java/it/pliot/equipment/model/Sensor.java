package it.pliot.equipment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Sensor  extends BaseEntity {

    private @Id String id;

    private String equipmentId;

    private String unitOfMeasurement;

    private String version;

    private String name;

    private String minVal;

    private String maxVal;

    private String downRedLimit;

    private String downYellowLimit;

    private String upRedLimit;

    private String upYellowLimit;

    private Date createdDttm;

    private Date updateDttm;

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

    public String getMinVal() {
        return minVal;
    }

    public void setMinVal(String minVal) {
        this.minVal = minVal;
    }

    public String getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(String maxVal) {
        this.maxVal = maxVal;
    }

    public String getDownRedLimit() {
        return downRedLimit;
    }

    public void setDownRedLimit(String downRedLimit) {
        this.downRedLimit = downRedLimit;
    }

    public String getDownYellowLimit() {
        return downYellowLimit;
    }

    public void setDownYellowLimit(String downYellowLimit) {
        this.downYellowLimit = downYellowLimit;
    }

    public String getUpRedLimit() {
        return upRedLimit;
    }

    public void setUpRedLimit(String upRedLimit) {
        this.upRedLimit = upRedLimit;
    }

    public String getUpYellowLimit() {
        return upYellowLimit;
    }

    public void setUpYellowLimit(String upYellowLimit) {
        this.upYellowLimit = upYellowLimit;
    }


    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public String getName() {
        return name;
    }




}
