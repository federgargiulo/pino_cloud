package it.pliot.equipment.service.ext;

import it.pliot.equipment.Const;
import it.pliot.equipment.io.UserGrpTO;
import it.pliot.equipment.io.UserTO;
import org.keycloak.representations.idm.ClientRepresentation;
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


    public static ClientRepresentation createEdgeWebClient(String clientId , String redirect ) {
        ClientRepresentation client = new ClientRepresentation();
        client.setClientId( clientId );
        client.setName("Web client for tenant "   );
        client.setEnabled(true);
        client.setPublicClient(true); // importante per SPA
        client.setProtocol("openid-connect");
        client.setRedirectUris(List.of(redirect)); // o l'URL della tua app
        client.setWebOrigins(List.of("*")); // oppure lista esplicita come "http://localhost:4200"
        client.setDirectAccessGrantsEnabled(true); // utile per login con username/password
        client.setStandardFlowEnabled(true); // abilita authorization code flow (PKCE per SPA)
        client.setImplicitFlowEnabled(false); // sconsigliato per le SPA moderne
        client.setServiceAccountsEnabled(false); // non serve per app frontend
        client.setRootUrl("http://localhost:4200"); // se serve
        client.setAttributes(Map.of(
                "pkce.code.challenge.method", "S256",  // PKCE per sicurezza
                "post.logout.redirect.uris", "+",      // opzionale: supporta redirect dopo logout
                "access.token.lifespan", "300"         // opzionale: token TTL personalizzato
        ));
        return client;

    }


    public static ClientRepresentation createEdgeConfidentialClient(String clientId ) {
        ClientRepresentation client = new ClientRepresentation();
        client.setClientId( clientId );
        client.setName("System API Client for tenant " );
        client.setEnabled(true);
        client.setProtocol("openid-connect");
        client.setPublicClient(false); // <== confidential client
        client.setServiceAccountsEnabled(true); // <== importante per client_credentials flow
        client.setDirectAccessGrantsEnabled(false); // opzionale
        client.setStandardFlowEnabled(false); // opzionale
        client.setImplicitFlowEnabled(false); // disabilitato per sicurezza
        client.setAuthorizationServicesEnabled(false); // opzionale, attiva solo se usi fine-grained authz

// eventuale redirect URI se usi anche authorization code flow (di solito no per confidential client)
        client.setRedirectUris(Collections.emptyList());
        return client;

    }
}
