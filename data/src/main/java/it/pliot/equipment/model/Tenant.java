package it.pliot.equipment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Tenant extends BaseEntity {
    @Id
    private String id;
    private String name;
    private String description;

    private Date createdDttm;

    private Date updateDttm;

    @Override
    public Date getCreatedDttm() {
        return createdDttm;
    }

    @Override
    public void setCreatedDttm(Date createdDttm) {
        this.createdDttm = createdDttm;
    }

    @Override
    public Date getUpdateDttm() {
        return updateDttm;
    }

    @Override
    public void setUpdateDttm(Date updateDttm) {
        this.updateDttm = updateDttm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
