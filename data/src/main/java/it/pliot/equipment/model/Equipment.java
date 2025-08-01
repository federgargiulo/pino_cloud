package it.pliot.equipment.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Equipment extends BaseEntity{

    @Id
    private String id;

    public Equipment( String name ){
        this.name = name;
    }

    private String tenant;

    public Date getReceivedFromEdgeDttm() {
        return receivedFromEdgeDttm;
    }

    public void setReceivedFromEdgeDttm(Date receivedFromEdgeDttm) {
        this.receivedFromEdgeDttm = receivedFromEdgeDttm;
    }

    public String getEdgeId() {
        return edgeId;
    }

    public void setEdgeId(String edgeId) {
        this.edgeId = edgeId;
    }

    @Column( name = "received_from_edge_timestamp" , nullable = true )
    private Date receivedFromEdgeDttm;

    @Column( name = "edge_id" , nullable = true )
    private String edgeId;


    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    private Date createdDttm;

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



    public void setStatus(String status) {
        this.status = status;
    }





    private Date updateDttm;


    private String status;

    public String getStatus() {
        return status;
    }

    public Equipment(){}

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return id.equals(equipment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "equipmentId='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }
}
