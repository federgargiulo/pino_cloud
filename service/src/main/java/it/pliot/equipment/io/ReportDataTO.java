package it.pliot.equipment.io;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

import java.math.BigDecimal;
import java.util.Date;


public class ReportDataTO {

    private String id;

    private String tenantId;

    private String equipmentId;

     private String signalId;

     private Date referenceTimestamp;

    private Date creationDttm;

    private Integer yearVal;

    public Date getCreationDttm() {
        return creationDttm;
    }

    public void setCreationDttm(Date creationDttm) {
        this.creationDttm = creationDttm;
    }

     private Integer monthVal;

     private Integer dayVal;

     private Integer hourVal;

    private Integer minuteVal;

    private BigDecimal meanVal;

    private BigDecimal minVal;

     private BigDecimal maxVal;

      private BigDecimal countOfMeasure;


    private Date receivedFromEdgeDttm;

    private String edgeId;

    public Date getReceivedFromEdgeDttm() {
        return receivedFromEdgeDttm;
    }

    public void setReceivedFromEdgeDttm(Date receivedFromEdgeDttm) {
        this.receivedFromEdgeDttm = receivedFromEdgeDttm;
    }

    public String getEdgeId() {
        return edgeId;
    }

    public void setEdgeId(String edgeId) {
        this.edgeId = edgeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getSignalId() {
        return signalId;
    }

    public void setSignalId(String signalId) {
        this.signalId = signalId;
    }

    public Date getReferenceTimestamp() {
        return referenceTimestamp;
    }

    public void setReferenceTimestamp(Date referenceTimestamp) {
        this.referenceTimestamp = referenceTimestamp;
    }

    public Integer getYearVal() {
        return yearVal;
    }

    public void setYearVal(Integer yearVal) {
        this.yearVal = yearVal;
    }

    public Integer getMonthVal() {
        return monthVal;
    }

    public void setMonthVal(Integer monthVal) {
        this.monthVal = monthVal;
    }

    public Integer getDayVal() {
        return dayVal;
    }

    public void setDayVal(Integer dayVal) {
        this.dayVal = dayVal;
    }

    public Integer getHourVal() {
        return hourVal;
    }

    public void setHourVal(Integer hourVal) {
        this.hourVal = hourVal;
    }

    public Integer getMinuteVal() {
        return minuteVal;
    }

    public void setMinuteVal(Integer minuteVal) {
        this.minuteVal = minuteVal;
    }

    public BigDecimal getMeanVal() {
        return meanVal;
    }

    public void setMeanVal(BigDecimal meanVal) {
        this.meanVal = meanVal;
    }

    public BigDecimal getMinVal() {
        return minVal;
    }

    public void setMinVal(BigDecimal minVal) {
        this.minVal = minVal;
    }

    public BigDecimal getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(BigDecimal maxVal) {
        this.maxVal = maxVal;
    }

    public BigDecimal getCountOfMeasure() {
        return countOfMeasure;
    }

    public void setCountOfMeasure(BigDecimal countOfMeasure) {
        this.countOfMeasure = countOfMeasure;
    }




}
