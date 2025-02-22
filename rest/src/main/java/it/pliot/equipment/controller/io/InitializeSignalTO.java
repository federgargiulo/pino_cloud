package it.pliot.equipment.controller.io;

import it.pliot.equipment.io.SignalTO;

import java.io.Serializable;

public class InitializeSignalTO implements Serializable  {

    private SignalTO signalTO;

    private String equipmentName;

    private String accessKey;

    public SignalTO getSignalTO() {
        return signalTO;
    }

    public void setSignalTO(SignalTO signalTO) {
        this.signalTO = signalTO;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
}
