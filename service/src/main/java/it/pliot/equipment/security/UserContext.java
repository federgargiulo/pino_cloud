package it.pliot.equipment.security;

import it.pliot.equipment.Const;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

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
              u.setTenantId(  exractTenantId( jwt.getClaim( "groups") ) );
              return u;
          }
          return null;

    }

    private static String exractTenantId(Object groups) {
        if ( groups instanceof Collection ){
            Iterator<String> i = ( ( Collection ) groups ).iterator();
            while ( i.hasNext() ){
                String x = extractTenantName( i.next() );
                if ( x != null && x.length() > 0 )
                    return x;
            }
        }
        return null;
    }
    private static int START_INDEX = Const.GROUP_PREFIX.length() + 1;

    private static String extractTenantName( String x ){
        if ( x == null ) return null;
        int i = x.indexOf( Const.GROUP_PREFIX );
        if ( i < 0 )
            return null;
        int endIndex = x.length();
        if ( START_INDEX >= endIndex  )
            return  null;

        return x.substring( START_INDEX , endIndex );
    }

}
