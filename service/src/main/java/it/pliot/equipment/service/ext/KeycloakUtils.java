package it.pliot.equipment.service.ext;

import it.pliot.equipment.io.TenantTO;
import it.pliot.equipment.io.UserTO;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Collections;

public class KeycloakUtils {

    public static UserRepresentation initUser(UserTO user){
        UserRepresentation u = new UserRepresentation( );
        u.setFirstName( user.getFirstName() );
        u.setLastName( user.getLastName() );
        u.setUsername( user.getUsername() );
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue("password123");
        credential.setTemporary(false);
        u.setCredentials(Collections.singletonList(credential));
        return u;
    }

    public static GroupRepresentation initGrp(String grpName) {
        GroupRepresentation g = new GroupRepresentation();
        g.setName( grpName );
        return g;
    }
}
