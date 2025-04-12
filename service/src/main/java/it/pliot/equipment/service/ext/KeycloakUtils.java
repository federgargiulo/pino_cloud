package it.pliot.equipment.service.ext;

import it.pliot.equipment.Const;
import it.pliot.equipment.io.UserGrpTO;
import it.pliot.equipment.io.UserTO;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.*;

public class KeycloakUtils {


    public static UserRepresentation initUser(UserTO user , String ... groups){


        UserRepresentation u = new UserRepresentation( );
        u.setFirstName( user.getFirstName() );
        u.setLastName( user.getLastName() );
        u.setUsername( user.getUserId() );
        u.setEmail( user.getEmail() );
        u.setGroups( getGrpNames( user.getUsrGrp() ) );
        u.setEnabled( true );
        u.setAttributes( getAttributes( user ) );
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(user.getPassword());
        credential.setTemporary(false);
        u.setCredentials(Collections.singletonList(credential));
        return u;
    }

    private static Map<String, List<String>> getAttributes( UserTO u ) {
        Map<String, List<String>> attributes = new HashMap<>();
        ArrayList<String> t = new ArrayList<String>( );
        t.add( u.getTenant() );
        attributes.put( Const.TENANT_ID_USER_ATTRIBUTE , t );
        return attributes;
    }

    private static List<String> getGrpNames(List<UserGrpTO> groups ){
        ArrayList<String> g = new ArrayList<String>();
        if ( groups == null )
            return new ArrayList<String>();
        groups.forEach( x -> g.add( x.getGrpName() ) );
        return g;

    }
    public static GroupRepresentation initGrp(String grpName) {
        GroupRepresentation g = new GroupRepresentation();
        g.setName( Const.GROUP_PREFIX + grpName );
        return g;
    }

    public static UserRepresentation getUpdateUser(UserTO user  ){
        UserRepresentation u = new UserRepresentation( );
        u.setId(user.getIdpId());
        u.setFirstName( user.getFirstName() );
        u.setLastName( user.getLastName() );
        u.setUsername( user.getUserId() );
        u.setEmail( user.getEmail() );
        u.setEnabled( true );
        return u;
    }


}
