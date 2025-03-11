package it.pliot.equipment.security;

public class UserContext {

    private static User DEVELOPMENET_USER = null;
    static{
        DEVELOPMENET_USER = new User();
        DEVELOPMENET_USER.setUserId( "dev_user");
        DEVELOPMENET_USER.setTenantId("dev_tenant");
    }

    public static String currentUserId(){
        return DEVELOPMENET_USER.getUserId();
    }

    public static User currentUser(){
        return DEVELOPMENET_USER;
    }

    public static void setUser( User u ){}
}
