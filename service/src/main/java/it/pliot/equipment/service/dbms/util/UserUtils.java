package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.UserIO;
import it.pliot.equipment.model.User;

public class UserUtils extends BaseConvertUtil<User, UserIO>{


    private static UserUtils INSTANCE = new UserUtils();

    public static UserUtils instance(){
        return INSTANCE;
    }
    private UserUtils(){}

    @Override
    public User io2data(UserIO in) {
        if ( in == null ) return null;
        User out = new User();
        out.setAddress( in.getAddress() );
        out.setAge( in.getAge());
        out.setName( in.getName() );
        out.setEmail( in.getEmail() );
        out.setGender( in.getGender() );
        out.setPassword( in.getPassword() );
        out.setPhone( in.getPhone() );
        out.setRoles( RoleUtils.instance().converListIO2data( in.getRoles() ));
        out.setCreatedDttm( in.getCreatedDttm() );
        out.setUpdateDttm( in.getCreatedDttm() );
        out.setType( in.getType() );
        return out;
    }

    @Override
    public UserIO data2io(User in) {
        if ( in == null ) return null;
        UserIO out = new UserIO();
        out.setAddress( in.getAddress() );
        out.setAge( in.getAge());
        out.setName( in.getName() );
        out.setEmail( in.getEmail() );
        out.setGender( in.getGender() );
        out.setPassword( in.getPassword() );
        out.setPhone( in.getPhone() );
        out.setRoles( RoleUtils.instance().converListData2IO(  in.getRoles() ));
        out.setCreatedDttm( in.getCreatedDttm() );
        out.setUpdateDttm( in.getCreatedDttm() );
        out.setType( in.getType() );
        return out;

    }
}
