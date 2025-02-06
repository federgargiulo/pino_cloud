package it.pliot.equipment.io;

import java.io.Serializable;
import java.util.Date;

public class MeasureIO implements Serializable {

    private String id;

    private String sensorId;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getMesure_dttm() {
        return mesure_dttm;
    }

    public void setMesure_dttm(Date mesure_dttm) {
        this.mesure_dttm = mesure_dttm;
    }

    private String value;

    private Date mesure_dttm;

}
