package it.pliot.equipment.io;

import java.util.Objects;


public class RoleTO extends BaseTO {

    public static RoleTO newroleio(String name , String description ){
        RoleTO r = new RoleTO();
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
        RoleTO role1 = (RoleTO) o;
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
