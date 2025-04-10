package it.pliot.equipment.service.ext;

import it.pliot.equipment.Const;
import it.pliot.equipment.io.TenantTO;
import it.pliot.equipment.io.UserTO;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KeycloakUtils {

    public static UserRepresentation initUser(UserTO user , String ... groups){
        UserRepresentation u = new UserRepresentation( );
        u.setFirstName( user.getFirstName() );
        u.setLastName( user.getLastName() );
        u.setUsername( user.getUserId() );
        u.setEmail( user.getEmail() );
        u.setGroups( toList( groups ) );
        u.setEnabled( true );
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(user.getPassword());
        credential.setTemporary(false);
        u.setCredentials(Collections.singletonList(credential));
        return u;
    }
    private static List<String> toList( String ... groups ){
        if ( groups == null )
            return new ArrayList<String>();
        return Arrays.stream(groups).toList();

    }
    public static GroupRepresentation initGrp(String grpName) {
        GroupRepresentation g = new GroupRepresentation();
        g.setName( Const.GROUP_PREFIX + grpName );
        return g;
    }

    public static UserRepresentation updateUser(UserTO user , String ... groups){
        UserRepresentation u = new UserRepresentation( );
        u.setId(user.getIdpId());
        u.setFirstName( user.getFirstName() );
        u.setLastName( user.getLastName() );
        u.setUsername( user.getUserId() );
        u.setEmail( user.getEmail() );
        u.setGroups( toList( groups ) );
        u.setEnabled( true );
        return u;
    }


}
