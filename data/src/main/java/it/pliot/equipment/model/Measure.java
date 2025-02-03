package it.pliot.equipment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Date;


@Entity
public class Measure  implements Serializable {
    @Id
    private String mesaureId;

    private String sensorId;

    public String getMesaureId() {
        return mesaureId;
    }

    public void setMesaureId(String mesaureId) {
        this.mesaureId = mesaureId;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public Date getMesure_dttm() {
        return mesure_dttm;
    }

    public void setMesure_dttm(Date mesure_dttm) {
        this.mesure_dttm = mesure_dttm;
    }

    private String measure;

    private Date mesure_dttm;


}
