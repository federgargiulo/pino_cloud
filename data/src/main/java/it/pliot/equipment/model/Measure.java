package it.pliot.equipment.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


@Entity
public class Measure  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String srcId;

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    @Column(nullable = false)
    private Integer yearVal;

    @Column(nullable = false)
    private Integer monthVal;

    @Column(nullable = false)
    private Integer dayVal;

    @Column(nullable = false)
    private Integer hourVal;

    @Column(nullable = false)
    private Integer minuteVal;

    @Column(nullable = false)
    private Integer secRound10Val;

    @Column(nullable = false)
    private Integer secRound30Val;


    private String tenantId;

    private String equipmentId;

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

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Measure measure = (Measure) o;
        return Objects.equals(id, measure.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


    private String val;

    private Date measureDttm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getVal() {
        return val;
    }

    public void setValore(String val) {
        this.val = val;
    }

    public Date getMeasureDttm() {
        return measureDttm;
    }

    public void setMeasureDttm(Date measureDttm) {
        this.measureDttm = measureDttm;
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

    public Integer getSecRound10Val() {
        return secRound10Val;
    }

    public void setSecRound10Val(Integer secRound10Val) {
        this.secRound10Val = secRound10Val;
    }

    public Integer getSecRound30Val() {
        return secRound30Val;
    }

    public void setSecRound30Val(Integer secRound30Val) {
        this.secRound30Val = secRound30Val;
    }

    @PrePersist
    @PreUpdate
    public void computeDerivedFields() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(measureDttm);
        this.yearVal = cal.get(Calendar.YEAR);
        this.monthVal = cal.get(Calendar.MONTH) + 1; // Months are 0-based in Calendar
        this.dayVal = cal.get(Calendar.DAY_OF_MONTH);
        this.hourVal = cal.get(Calendar.HOUR_OF_DAY);
        this.minuteVal = cal.get(Calendar.MINUTE);
        // Round seconds up to 00 or 30
        int sec = cal.get(Calendar.SECOND);
        this.secRound10Val = ((sec) / 10) * 10;
        this.secRound30Val = ((sec) / 30) * 30;

    }
}
