package it.pliot.equipment.service.ext;

import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public abstract class BaseKeycloakExtension {
    private static final Logger log = LoggerFactory.getLogger(KeycloakUserExtension.class);


    private RealmResource realm;

    @Value("${pliot.keycloak.url}")
    private String url;

    @Value("${pliot.keycloak.realmName}")
    private String realmName;

    @Value("${pliot.keycloak.realmManaged}")
    private String realmManaged;

    public RealmResource getRealm() {
        return realm;
    }

    public void setRealm(RealmResource realm) {
        this.realm = realm;
    }

    protected Keycloak openKeycloak() {
        return Keycloak.getInstance(
                url,
                realmName,
                user,
                password,
                clientId);
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRealmName() {
        return realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }

    public String getRealmManaged() {
        return realmManaged;
    }

    public void setRealmManaged(String realmManaged) {
        this.realmManaged = realmManaged;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getServerBaseUrl() {
        return serverBaseUrl;
    }

    public void setServerBaseUrl(String serverBaseUrl) {
        this.serverBaseUrl = serverBaseUrl;
    }

    @Value("${pliot.keycloak.user}")
    private String user;

    @Value("${pliot.keycloak.password}")
    private String password;

    @Value("${pliot.keycloak.clientId}")
    private String clientId;

    @Value(("${pliot.server.url}"))
    private String serverBaseUrl;
    public Optional<GroupRepresentation> findGrpByName(RealmResource realm  , String grpName){
        Response res = null;
        try{
            List<GroupRepresentation> groups = realm
                    .groups()
                    .groups( grpName , 0, 10);
            return groups.isEmpty() ? Optional.empty() : Optional.of(groups.get(0));
        }finally {
            if ( res != null ){
                try{ res.close(); } catch (Exception e) {}
            }
        }
    }

    public Optional<String> findGrpIdByName(  String grpName){
        Keycloak keycloak = null;
        try{
            keycloak = openKeycloak();
            RealmResource realm = keycloak.realm( getRealmManaged() );

            Optional<GroupRepresentation> groups = findGrpByName( realm , grpName );
            return groups.isEmpty() ? Optional.empty() : Optional.of( groups.get().getId() );

        }finally {
            if ( keycloak != null ){
                try{ keycloak.close(); } catch (Exception e) {}
            }
        }
    }

    private HashMap<String, String > mapGrpNameId = new HashMap<String,String>();

    public Optional<String> getGroupIdByName( String x ){
        String id = mapGrpNameId.get( x ) ;
        if ( id != null )
            return  Optional.of( id );;
        Optional<String> op = findGrpIdByName( x );
        if ( op.isPresent() )
            mapGrpNameId.put( x , op.get() );
        return op;
    }


}
