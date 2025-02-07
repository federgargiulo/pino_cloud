package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.RoleTO;
import it.pliot.equipment.model.Role;

public class RoleUtils extends BaseConvertUtil< Role, RoleTO>{

    private static RoleUtils INSTANCE = new RoleUtils();

    public static RoleUtils instance(){
        return INSTANCE;
    }
    private RoleUtils(){}

    public RoleTO data2io(Role data ){
        if ( data == null ) return null;
        RoleTO rio  = new RoleTO();
        rio.setRole( data.getRole() );
        rio.setDescription( data.getDescription() );
        return rio;
    }

    public Role io2data( RoleTO rio ){
        if ( rio == null ) return null;
        Role data  = new Role();
        data.setRole( rio.getRole() );
        data.setDescription( rio.getDescription() );
        return data;
    }



}
