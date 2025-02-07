package it.pliot.equipment.io;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EquipmentTO implements Serializable {

    public static EquipmentTO newEquipment(String name , String tenant ){
        EquipmentTO e = new EquipmentTO();
        e.setName( name );
        e.setTenant( tenant );
        return e;
    }

    private String tenant;

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public EquipmentTO(String name ){
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

    public void setSensors(List<SensorTO> sensors) {
        this.sensors = sensors;
    }

    public List<SensorTO> getSensors() {
        if ( sensors == null )
            sensors = new ArrayList<SensorTO>();
        return sensors;
    }

    private List<SensorTO> sensors;

    public EquipmentTO(){}

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
