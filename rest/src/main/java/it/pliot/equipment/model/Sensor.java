package it.pliot.equipment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Sensor {

    private @Id String sensorId;

    private String equipmentId;

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String unitOfMeasurement;

    public String getSensorId() {
        return sensorId;
    }

    private String name;

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public String getName() {
        return name;
    }


}
