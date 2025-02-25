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

    private String email;

    private String profile;

    private String country;

    private String state;

    private String zipCode;

    private String address;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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
