package it.pliot.equipment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;
import java.util.Objects;

@Entity
public class EquipmentPuller {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String pullerId;

    private String idEquipment;

    private String tenant;

    private String url;

    private Integer intervalInSec;

    private Date nextExecutions;

    private Date lastStart;

    private Date lastEnd;

    private String lastExecutionReport;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private String apiKey;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentPuller that = (EquipmentPuller) o;
        return Objects.equals(pullerId, that.pullerId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pullerId);
    }

    private Date lastExecution;

    public String getPullerId() {
        return pullerId;
    }

    public void setPullerId(String pullerId) {
        this.pullerId = pullerId;
    }

    public String getIdEquipment() {
        return idEquipment;
    }

    public void setIdEquipment(String idEquipment) {
        this.idEquipment = idEquipment;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public Date getLastExecution() {
        return lastExecution;
    }

    public void setLastExecution(Date lastExecution) {
        this.lastExecution = lastExecution;
    }

    public Integer getIntervalInSec() {
        return intervalInSec;
    }

    public void setIntervalInSec(Integer intervalInSec) {
        this.intervalInSec = intervalInSec;
    }

    public Date getNextExecutions() {
        return nextExecutions;
    }

    public void setNextExecutions(Date nextExecutions) {
        this.nextExecutions = nextExecutions;
    }

    public Date getLastStart() {
        return lastStart;
    }

    public void setLastStart(Date lastStart) {
        this.lastStart = lastStart;
    }

    public Date getLastEnd() {
        return lastEnd;
    }

    public void setLastEnd(Date lastEnd) {
        this.lastEnd = lastEnd;
    }

    public String getLastExecutionReport() {
        return lastExecutionReport;
    }

    public void setLastExecutionReport(String lastExecutionReport) {
        this.lastExecutionReport = lastExecutionReport;
    }



}
