package it.pliot.equipment.io;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Sample {

    private String timestamp;

    @JsonProperty("voltage_V_L1")
    private double voltageVL1;

    @JsonProperty("voltage_V_L2")
    private double voltageVL2;

    @JsonProperty("voltage_V_L3")
    private double voltageVL3;

    @JsonProperty("current_A_L1")
    private double currentAL1;

    @JsonProperty("current_A_L2")
    private double currentAL2;

    @JsonProperty("current_A_L3")
    private double currentAL3;

    @JsonProperty("vibration_rms_mm_s")
    private double vibration;

    @JsonProperty("stator_temp_C")
    private double statorTemp;

    @JsonProperty("speed_rpm")
    private double speed;

    public Sample() {
        // costruttore vuoto per Jackson
    }

    // Getters e Setters

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getVoltageVL1() {
        return voltageVL1;
    }

    public void setVoltageVL1(double voltageVL1) {
        this.voltageVL1 = voltageVL1;
    }

    public double getVoltageVL2() {
        return voltageVL2;
    }

    public void setVoltageVL2(double voltageVL2) {
        this.voltageVL2 = voltageVL2;
    }

    public double getVoltageVL3() {
        return voltageVL3;
    }

    public void setVoltageVL3(double voltageVL3) {
        this.voltageVL3 = voltageVL3;
    }

    public double getCurrentAL1() {
        return currentAL1;
    }

    public void setCurrentAL1(double currentAL1) {
        this.currentAL1 = currentAL1;
    }

    public double getCurrentAL2() {
        return currentAL2;
    }

    public void setCurrentAL2(double currentAL2) {
        this.currentAL2 = currentAL2;
    }

    public double getCurrentAL3() {
        return currentAL3;
    }

    public void setCurrentAL3(double currentAL3) {
        this.currentAL3 = currentAL3;
    }

    public double getVibration() {
        return vibration;
    }

    public void setVibration(double vibration) {
        this.vibration = vibration;
    }

    public double getStatorTemp() {
        return statorTemp;
    }

    public void setStatorTemp(double statorTemp) {
        this.statorTemp = statorTemp;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
