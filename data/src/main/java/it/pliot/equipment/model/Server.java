package it.pliot.equipment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import java.util.Date;
import java.util.Objects;

@Entity
public class Server  {

    @Id
    private String id;

    @Lob
    @Column(name = "base64certificate"  )
    private String base64certificate;

    @Lob
    @Column(name = "base64keystore" )
    private String base64keystore;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}
