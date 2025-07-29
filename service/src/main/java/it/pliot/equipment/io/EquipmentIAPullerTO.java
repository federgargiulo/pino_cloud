package it.pliot.equipment.io;

import it.pliot.equipment.model.EquipmentIAPuller;
import it.pliot.equipment.model.EquipmentPuller;

import java.util.Date;
import java.util.Objects;

public class EquipmentIAPullerTO {

    private String iaPullerId;

    private String equipmentId;

    private String tenant;

    private String url;

    private String apiKey;

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




    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentIAPuller that = (EquipmentIAPuller) o;
        return Objects.equals(iaPullerId, that.getIaPullerId() );
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(iaPullerId);
    }

    private Date lastExecution;

    public String getIaPullerId() {
        return iaPullerId;
    }

    public void setIaPullerId(String iaPullerId) {
        this.iaPullerId = iaPullerId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
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
