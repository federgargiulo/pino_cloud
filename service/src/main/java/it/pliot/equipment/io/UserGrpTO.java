package it.pliot.equipment.io;

import java.util.Objects;


public class UserGrpTO extends BaseTO {

    public static UserGrpTO newroleio(String name , String description ){
        UserGrpTO r = new UserGrpTO();
        r.setGrpName( name );
        r.setDescription( description );
        return r;
    }

    private String grpName;

    private String description;

    public String getGrpName() {
        return grpName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGrpTO role1 = (UserGrpTO) o;
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
