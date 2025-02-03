package it.pliot.equipment.io;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EquipmentIO implements Serializable {
    public  EquipmentIO( String name ){
        this.name = name;
    }
    private  String equipmentId;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSensors(List<SensorIO> sensors) {
        this.sensors = sensors;
    }

    public List<SensorIO> getSensors() {
        if ( sensors == null )
            sensors = new ArrayList<SensorIO>();
        return sensors;
    }

    private List<SensorIO> sensors;

    public EquipmentIO(){}

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getEquipmentId() {
        return equipmentId;
    }

    private Date createdDttm;

    private Date updateDttm;

    private String version;

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


    @Override
    public String toString() {
        return "Equipment{" +
                "equipmentId='" + equipmentId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }
}
