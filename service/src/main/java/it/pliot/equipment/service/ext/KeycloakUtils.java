package it.pliot.equipment.service.ext;

import it.pliot.equipment.Const;
import it.pliot.equipment.io.UserGrpTO;
import it.pliot.equipment.io.UserTO;
import org.keycloak.representations.idm.*;

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
        u.setUsername( user.getUserName() );
        u.setEmail( user.getEmail() );
        u.setEnabled( true );
        return u;
    }


    public static ClientRepresentation createEdgeWebClient(String clientId, String redirect) {
        ClientRepresentation client = new ClientRepresentation();
        client.setClientId(clientId);
        client.setName("Web client for tenant ");
        client.setEnabled(true);
        client.setPublicClient(true);
        client.setProtocol("openid-connect");
        client.setRedirectUris(List.of(redirect));
        client.setWebOrigins( List.of("http://localhost:8080") );
        client.setDirectAccessGrantsEnabled(true);
        client.setStandardFlowEnabled(true);
        client.setImplicitFlowEnabled(false);
        client.setServiceAccountsEnabled(false);
        client.setRootUrl("http://localhost:4200");

        client.setAttributes(Map.of(
                "pkce.code.challenge.method", "S256",
                "post.logout.redirect.uris", "+",
                "access.token.lifespan", "300"
        ));

        // --- Aggiungi il protocol mapper per il tenant ---
        ProtocolMapperRepresentation tenantMapper = new ProtocolMapperRepresentation();
        tenantMapper.setName("tenant");
        tenantMapper.setProtocol("openid-connect");
        tenantMapper.setProtocolMapper("oidc-usermodel-attribute-mapper");

        tenantMapper.setConfig(Map.of(
                "user.attribute", "tenant",         // da quale user attribute leggere
                "claim.name", "tenant",             // come si chiama nel token
                "jsonType.label", "String",         // tipo
                "id.token.claim", "true",           // mettilo nell’ID token
                "access.token.claim", "true",       // mettilo anche nell’access token
                "userinfo.token.claim", "true"      // (opzionale) anche in /userinfo
        ));

        client.setProtocolMappers(List.of(tenantMapper));

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
