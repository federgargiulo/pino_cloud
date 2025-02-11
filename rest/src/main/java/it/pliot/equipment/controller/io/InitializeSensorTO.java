package it.pliot.equipment.controller.io;

import it.pliot.equipment.io.SensorTO;

import java.io.Serializable;

public class InitializeSensorTO implements Serializable  {

    private SensorTO sensor;

    private String equipmentName;

    private String accessKey;

    public SensorTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorTO sensor) {
        this.sensor = sensor;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
}
