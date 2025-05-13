package it.pliot.equipment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;
@Entity
public class SystemHealthHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String metric;

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    private String component;

    private String tenat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenat() {
        return tenat;
    }

    public void setTenat(String tenat) {
        this.tenat = tenat;
    }

    public String getEdge() {
        return edge;
    }

    public void setEdge(String edge) {
        this.edge = edge;
    }

    private String edge;

    private Date report_dttm;

    private String val;

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public Date getReport_dttm() {
        return report_dttm;
    }

    public void setReport_dttm(Date report_dttm) {
        this.report_dttm = report_dttm;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
