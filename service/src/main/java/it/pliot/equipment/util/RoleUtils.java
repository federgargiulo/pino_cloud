package it.pliot.equipment.util;

import it.pliot.equipment.io.RoleIO;
import it.pliot.equipment.model.Role;

public class RoleUtils extends BaseConvertUtil< Role,RoleIO >{

    private static RoleUtils INSTANCE = new RoleUtils();

    public static RoleUtils instance(){
        return INSTANCE;
    }
    private RoleUtils(){}

    public RoleIO data2io( Role data ){
        if ( data == null ) return null;
        RoleIO rio  = new RoleIO();
        rio.setRole( data.getRole() );
        rio.setDescription( data.getDescription() );
        return rio;
    }

    public Role io2data( RoleIO rio ){
        if ( rio == null ) return null;
        Role data  = new Role();
        data.setRole( rio.getRole() );
        data.setDescription( rio.getDescription() );
        return data;
    }



}
