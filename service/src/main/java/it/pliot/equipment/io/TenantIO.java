package it.pliot.equipment.io;

import it.pliot.equipment.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;


public class TenantIO extends BaseEntity {
    private String id;

    private String name;
    private String description;

    public static TenantIO newrtenant(String edge, String descr) {
        TenantIO t = new TenantIO();
        t.setName( edge );
        t.setDescription( descr );
        return t;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TenantIO tenantIO = (TenantIO) o;
        return Objects.equals(id, tenantIO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
