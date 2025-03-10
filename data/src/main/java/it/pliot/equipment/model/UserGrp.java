package it.pliot.equipment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class UserGrp {
    @Id
    private String grpName;

    private String description;

    private String idpGrp;

    public String getIdpGrp() {
        return idpGrp;
    }

    public void setIdpGrp(String idpGrp) {
        this.idpGrp = idpGrp;
    }

    public String getGrpName() {
        return grpName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGrp role1 = (UserGrp) o;
        return Objects.equals(grpName, role1.grpName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grpName);
    }

    public void setGrpName(String grpName) {
        this.grpName = grpName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
