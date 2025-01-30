package it.cogito.equipment.io;


import java.io.Serializable;

public class SensorIO implements Serializable {

    private String sensorId;

    private String equipmentId;

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
