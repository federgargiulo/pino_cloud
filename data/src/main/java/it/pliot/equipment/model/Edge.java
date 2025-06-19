package it.pliot.equipment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Lob;


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

    @Lob
    @Column(name = "base64certificate")
    private String base64certificate;

    @Lob
    @Column(name = "base64keystore")
    private String base64keystore;

    public String getBase64certificate() {
        return base64certificate;
    }

    public void setBase64certificate(String base64certificate) {
        this.base64certificate = base64certificate;
    }

    public String getBase64keystore() {
        return base64keystore;
    }

    public void setBase64keystore(String base64keystore) {
        this.base64keystore = base64keystore;
    }

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
