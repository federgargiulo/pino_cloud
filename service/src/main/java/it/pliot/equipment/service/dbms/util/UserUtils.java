package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.UserTO;
import it.pliot.equipment.model.User;

public class UserUtils extends BaseConvertUtil<User, UserTO>{


    private static UserUtils INSTANCE = new UserUtils();

    public static UserUtils instance(){
        return INSTANCE;
    }
    private UserUtils(){}

    @Override
    public User io2data(UserTO in) {
        if ( in == null ) return null;
        User out = new User();
        out.setId(in.getUsername());
        out.setAddress( in.getAddress() );
        out.setAge( in.getAge());
        out.setFirstName(  in.getFirstName() );
        out.setLastName( in.getLastName() );
        out.setEmail( in.getEmail() );
        out.setGender( in.getGender() );
        out.setIdpId( in.getIdpId() );
        out.setPhone( in.getPhone() );
        out.setRoles( RoleUtils.instance().converListIO2data( in.getRoles() ));
        out.setCreatedDttm( in.getCreatedDttm() );
        out.setUpdateDttm( in.getCreatedDttm() );
        out.setType( in.getType() );
        return out;
    }

    @Override
    public UserTO data2io(User in) {
        if ( in == null ) return null;
        UserTO out = new UserTO();
        out.setUsername(in.getId());
        out.setAddress( in.getAddress() );
        out.setAge( in.getAge());
        out.setFirstName( in.getFirstName() );
        out.setLastName( in.getLastName() );
        out.setEmail( in.getEmail() );
        out.setGender( in.getGender() );
        out.setIdpId( in.getIdpId() );
        out.setPhone( in.getPhone() );
        out.setRoles( RoleUtils.instance().converListData2IO(  in.getRoles() ));
        out.setCreatedDttm( in.getCreatedDttm() );
        out.setUpdateDttm( in.getCreatedDttm() );
        out.setType( in.getType() );
        return out;

    }
}
