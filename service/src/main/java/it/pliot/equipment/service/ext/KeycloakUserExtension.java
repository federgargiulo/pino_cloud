package it.pliot.equipment.service.ext;

import it.pliot.equipment.io.TenantTO;
import it.pliot.equipment.io.UserTO;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;

import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;

import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.ProtocolMapperRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class KeycloakUserExtension {

    private static final Logger log = LoggerFactory.getLogger(KeycloakUserExtension.class);


    private RealmResource realm;

    @Value("${pliot.keycloak.url}")
    private String url;

    @Value("${pliot.keycloak.realmName}")
    private String realmName;

    @Value("${pliot.keycloak.realmManaged}")
    private String realmManaged;


    @Value("${pliot.keycloak.user}")
    private String user;

    @Value("${pliot.keycloak.password}")
    private String password;

    @Value("${pliot.keycloak.clientId}")
    private String clientId;

    @Value(("${pliot.server.url}"))
    private String serverBaseUrl;

    @PostConstruct
    public void postConstruct(){
        try{
            postConstructImpl();
        }catch ( Exception e ){
            log.error(" Keycloak not enabled " );
            log.error( " host {} " , url );
            log.error( "realmName {} " , realmName );
            log.error( "user {} " , user );
        }
    }
    private Keycloak keycloak = null;

    public void postConstructImpl() {

       // KeycloakGroupCmd.getInstance().createGroupIfNotExist( getRealm(), Const.DEV_TENANT_ID , Const.DEV_TENANT_NAME ) ;
    }

    private Keycloak openKeycloak() {
        return Keycloak.getInstance(
                url,
                realmName,
                user,
                password,
                clientId);
    }





    public UserTO createUser(UserTO user , String ... groupId ){
        Response response = null;
        Keycloak keycloak = null;
        try {
            keycloak = openKeycloak();
            response =   keycloak.realm( realmManaged ).users().create( KeycloakUtils.initUser(user  ) );
            if (response.getStatus() == 201) {
                String location = response.getHeaderString("Location");
                String id = location.substring(location.lastIndexOf("/") + 1);
                user.setIdpId( id  );
                return user;

            } else {
                System.out.println( response.getEntity() );
                throw new RuntimeException( " unable to create user ok keycloak: " + user.getUserId()  );
            }
        }finally {
            if ( response != null ){
                try{
                    response.close();
                }catch (Exception e ){
                    log.error( e.getMessage() );
                }
            }
            if ( keycloak != null ){
                try{
                    keycloak.close();
                }catch (Exception e ){
                    log.error( e.getMessage() );
                }
            }
        }
    }

    public void createTenantGRP(TenantTO x){
        Response response = null;
        Keycloak keycloak = null;
        try {
            keycloak = openKeycloak();
            RealmResource realm = keycloak.realm( realmManaged );
            createWebEdgeClient(x, realm);
            createApplicationClient(x, realm);

        }finally {
            if ( response != null ){
                try{
                    response.close();
                }catch (Exception e ){
                    log.error( e.getMessage() );
                }
            }
            if ( keycloak != null ){
                try{
                    keycloak.close();
                }catch (Exception e ){
                    log.error( e.getMessage() );
                }
            }
        }
    }

    private static void createApplicationClient(TenantTO x, RealmResource realm) {
        String apiWebClient = x.getTenantId() + "_system_api";

        // Controllo se esiste già
        List<ClientRepresentation> existingClients = realm.clients().findByClientId(apiWebClient);
        if (existingClients != null && !existingClients.isEmpty()) {
            return;
        }

        // Creazione del client confidential
        Response response = realm.clients().create(KeycloakUtils.createEdgeConfidentialClient(apiWebClient));
        if (response.getStatus() != 201) {
            throw new RuntimeException("Errore nella creazione del client: " + response.getStatus());
        }

        // Estrai clientId (UUID reale) dalla location
        String location = response.getHeaderString("Location");
        String createdId = location.substring(location.lastIndexOf("/") + 1);
        ClientResource clientResource = realm.clients().get(createdId);

        // Crea il protocol mapper per inserire il tenant nel token
        ProtocolMapperRepresentation tenantMapper = new ProtocolMapperRepresentation();
        tenantMapper.setName("tenant-claim");
        tenantMapper.setProtocol("openid-connect");
        tenantMapper.setProtocolMapper("oidc-hardcoded-claim-mapper");

        Map<String, String> config = new HashMap<>();
        config.put("claim.name", "tenant");
        config.put("claim.value", x.getTenantId());
        config.put("claim.value.type", "String");
        config.put("access.token.claim", "true");
        config.put("id.token.claim", "true");
        config.put("userinfo.token.claim", "true");

        tenantMapper.setConfig(config);

        // Associazione del mapper al client
        clientResource.getProtocolMappers().createMapper(tenantMapper);
    }

    private void createWebEdgeClient(TenantTO x, RealmResource realm) {
        String webclientid = x.getTenantId() + "_web_edge";
        List<ClientRepresentation> elenco = realm.clients().findByClientId( webclientid );
        if ( elenco != null && elenco.size() == 0  )
            realm.clients().create( KeycloakUtils.createEdgeWebClient( webclientid , serverBaseUrl + "/*") );
    }

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
            RealmResource realm = keycloak.realm( realmManaged );

            Optional<GroupRepresentation> groups = findGrpByName( realm , grpName );
            return groups.isEmpty() ? Optional.empty() : Optional.of( groups.get().getId() );

        }finally {
            if ( keycloak != null ){
                try{ keycloak.close(); } catch (Exception e) {}
            }
        }
    }

    public void updateUser(UserTO user, Map<String,OperationType> groupIds) {
        Keycloak keycloak = null;
        try {
            keycloak = openKeycloak();
            RealmResource realm = keycloak.realm(realmManaged);

            // Recupera lo user
            var userResource = realm.users().get(user.getIdpId());

            // Rimuovi i gruppi attuali (opzionale)
            List<GroupRepresentation> currentGroups = userResource.groups();

            groupIds.forEach( ( k, v ) ->  {

                if ( OperationType.ADD == v  ) {
                    Optional<String> op = findGrpIdByName( k );
                    if ( op.isPresent() )
                        userResource.joinGroup( op.get() );
                }
                if ( OperationType.DELETE == v  ) {
                    Optional<String> op = findGrpIdByName(k);
                    if (op.isPresent())
                        userResource.leaveGroup( op.get() );
                }

            });
            // Aggiunge i nuovi
            userResource.update(KeycloakUtils.getUpdateUser(user ));

            log.info("Updated groups for user {}", user.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            log.error( "Failed to update groups for user " + user.getUserId() , e);
            throw new RuntimeException("Unable to update user groups", e);
        } finally {
            if (keycloak != null) {
                try {
                    keycloak.close();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
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
