package it.pliot.equipment.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class UserContext {

    private static JwtUser DEVELOPMENET_USER = null;
    static{
        DEVELOPMENET_USER = new JwtUser();
        DEVELOPMENET_USER.setUserId( "dev_user");
        DEVELOPMENET_USER.setTenantId("dev_tenant");
    }

    public static String currentUserId(){
        return DEVELOPMENET_USER.getUserId();
    }

    public static JwtUser currentUser(){
          JwtUser u = new JwtUser();
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          if (authentication.getPrincipal() instanceof Jwt jwt) {
              u.setUserId( jwt.getClaim("preferred_username") );
              u.setFirstName( jwt.getClaim("given_name") );
              u.setLastName( jwt.getClaim("family_name") );
              u.setEmail( jwt.getClaim("email") );
              return u;
          }
          return null;

    }

    public static void setUser( JwtUser u ){}
}
