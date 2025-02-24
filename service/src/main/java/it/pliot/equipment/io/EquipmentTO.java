package it.pliot.equipment.io;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EquipmentTO implements Serializable {
    private String tenant;
    private String equipmentId;
    private String name;
    private String status;

    private List<SignalTO> signals;

    public static EquipmentTO newEquipment(String name , String tenant ){
        EquipmentTO e = new EquipmentTO();
        e.setName( name );
        e.setTenant( tenant );
        return e;
    }



    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public EquipmentTO(String name ){
        this.name = name;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSignals(List<SignalTO> signals) {
        this.signals = signals;
    }

    public List<SignalTO> getSignals() {
        if ( signals == null )
            signals = new ArrayList<SignalTO>();
        return signals;
    }



    public EquipmentTO(){}

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public void setName(String name) {
        this.name = name;
    }



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
