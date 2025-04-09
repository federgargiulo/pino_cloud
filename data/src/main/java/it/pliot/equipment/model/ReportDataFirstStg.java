package it.pliot.equipment.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity( name = "report_data_first_stg" )
@SequenceGenerator(
        name = "report_data_first_stg_seq",
        sequenceName = "report_data_first_stg_seq",
        allocationSize = 1  // Imposta il passo a 1
)
public class ReportDataFirstStg {

    @Id
    private String id;

    @Column( name = "tenant_id" , nullable = false )
    private String tenantId;

    @Column( name = "equipment_id" , nullable = false )
    private String equipmentId;

    @Column( name = "signal_id" , nullable = false )
    private String signalId;

    @Column( name = "reference_timestamp" , nullable = false )
    private Date referenceTimestamp;


    @Column( name = "create_timestamp" , nullable = false )
    private Date creationDttm;

    @Column( name = "year_val" , nullable = false )
    private Integer yearVal;

    public Date getCreationDttm() {
        return creationDttm;
    }

    public void setCreationDttm(Date creationDttm) {
        this.creationDttm = creationDttm;
    }

    @Column( name = "month_val" , nullable = false )
    private Integer monthVal;

    @Column( name = "day_val" , nullable = false )
    private Integer dayVal;

    @Column( name = "hour_val" , nullable = false )
    private Integer hourVal;

    @Column( name = "minute_val" , nullable = false )
    private Integer minuteVal;

    @Column( name = "mean_val" , nullable = false )
    private BigDecimal meanVal;

    @Column( name = "min_val" , nullable = false )
    private BigDecimal minVal;

    @Column( name = "max_val" , nullable = false )
    private BigDecimal maxVal;

    @Column( name = "count_of_measure" , nullable = false )
    private BigDecimal countOfMeasure;

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
