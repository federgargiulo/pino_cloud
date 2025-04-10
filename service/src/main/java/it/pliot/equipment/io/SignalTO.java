package it.pliot.equipment.io;


import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class SignalTO implements Serializable {

    public static SignalTO newEmptyInstance(String equipmentId , String signalId, String name , String tenant ){
        SignalTO io = new SignalTO();
        io.setEquipmentId( equipmentId );
        io.setName( name );

        io.setSignalId( signalId );
        io.setTenant( tenant );

        return io;
    }

   public static SignalTO newEmptyInstance(String equipmentId , String name , String tenant){
       return newEmptyInstance( equipmentId ,  UUID.randomUUID().toString() , name  , tenant );
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    private String tenant;


    private String signalId;

    private String equipmentId;

    private String version;

    private String name;

    private String minVal;

    private String maxVal;

    private String downRedLimit;

    private String downYellowLimit;

    private String upRedLimit;

    private Date createdDttm;

    private Date updateDttm;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDttm() {
        return createdDttm;
    }

    public void setCreatedDttm(Date createdDttm) {
        this.createdDttm = createdDttm;
    }

    public Date getUpdateDttm() {
        return updateDttm;
    }

    public void setUpdateDttm(Date updateDttm) {
        this.updateDttm = updateDttm;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMinVal() {
        return minVal;
    }

    public void setMinVal(String minVal) {
        this.minVal = minVal;
    }

    public String getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(String maxVal) {
        this.maxVal = maxVal;
    }

    public String getDownRedLimit() {
        return downRedLimit;
    }

    public void setDownRedLimit(String downRedLimit) {
        this.downRedLimit = downRedLimit;
    }

    public String getDownYellowLimit() {
        return downYellowLimit;
    }

    public void setDownYellowLimit(String downYellowLimit) {
        this.downYellowLimit = downYellowLimit;
    }

    public String getUpRedLimit() {
        return upRedLimit;
    }

    public void setUpRedLimit(String upRedLimit) {
        this.upRedLimit = upRedLimit;
    }

    public String getUpYellowLimit() {
        return upYellowLimit;
    }

    public void setUpYellowLimit(String upYellowLimit) {
        this.upYellowLimit = upYellowLimit;
    }

    private String upYellowLimit;


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

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String unitOfMeasurement;




    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public String getName() {
        return name;
    }

}
