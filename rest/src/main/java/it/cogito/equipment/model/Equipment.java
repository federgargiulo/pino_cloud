package it.cogito.equipment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Equipment {

    private @Id String equipmentId;
    public Equipment( String name ){
        equipmentId  = UUID.randomUUID().toString();
        this.name = name;

    }

    private String status;

    public String getStatus() {
        return status;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    @OneToMany
    private List<Sensor> sensors;

    public Equipment(){}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return equipmentId.equals(equipment.equipmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(equipmentId);
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
