package it.pliot.equipment.io;

import java.io.Serializable;
import java.util.Date;


public class SystemHealthHistoryTO implements Serializable {

    public SystemHealthHistoryTO(){}


    private String component;

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

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

    private String id;

    private String metric;

    private Date report_dttm;

    private String val;

    private String tenat;

    private String edge;


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
