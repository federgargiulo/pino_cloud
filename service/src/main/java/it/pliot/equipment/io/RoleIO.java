package it.pliot.equipment.io;

import jakarta.persistence.Id;

import java.util.Objects;


public class RoleIO extends BaseIO {

    public static RoleIO newroleio( String name , String description ){
        RoleIO r = new RoleIO();
        r.setRole( name );
        r.setDescription( description );
        return r;
    }

    private String role;

    private String description;

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleIO role1 = (RoleIO) o;
        return Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role);
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
