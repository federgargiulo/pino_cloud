package it.pliot.equipment.model;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;


import java.util.Date;

@Entity
public class Edge {

    @Id
    private String id;

    private String tenant;

    private String client;

    private Date registrationDttm;

    private String edgeName;

    private String edgeUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Date getRegistrationDttm() {
        return registrationDttm;
    }

    public void setRegistrationDttm(Date registrationDttm) {
        this.registrationDttm = registrationDttm;
    }

    public String getEdgeName() {
        return edgeName;
    }

    public void setEdgeName(String edgeName) {
        this.edgeName = edgeName;
    }

    public String getEdgeUrl() {
        return edgeUrl;
    }

    public void setEdgeUrl(String edgeUrl) {
        this.edgeUrl = edgeUrl;
    }
}
