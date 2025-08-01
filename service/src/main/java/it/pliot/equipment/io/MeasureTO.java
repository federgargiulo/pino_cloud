package it.pliot.equipment.io;

import java.io.Serializable;
import java.util.Date;

public class MeasureTO implements Serializable {

    private String id;

    private String srcId;

    private String signalId;

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    private String tenantId;

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

    private String equipmentId;


    private String val;

    private Date measureDttm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSignalId() {
        return signalId;
    }

    public void setSignalId(String signalId) {
        this.signalId = signalId;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public Date getMeasureDttm() {
        return measureDttm;
    }

    public void setMeasureDttm(Date measureDttm) {
        this.measureDttm = measureDttm;
    }



}
