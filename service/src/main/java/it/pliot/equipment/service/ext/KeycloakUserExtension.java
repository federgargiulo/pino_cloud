package it.pliot.equipment.service.ext;

import it.pliot.equipment.io.TenantTO;
import it.pliot.equipment.io.UserTO;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;

import org.keycloak.admin.client.resource.RealmResource;

import org.keycloak.representations.idm.GroupRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
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





    public UserTO createUser(UserTO user){
        Response response = null;
        Keycloak keycloak = null;
        try {
            keycloak = openKeycloak();

            response =   keycloak.realm( realmManaged ).users().create(KeycloakUtils.initUser(user));
            if (response.getStatus() == 201) {
                String location = response.getHeaderString("Location");
                String userId = location.substring(location.lastIndexOf("/") + 1);
                user.setIdpId( userId  );
                return user;

            } else {
                System.out.println( response.getEntity() );
                throw new RuntimeException( " unable to create user ok keycloak: " + user.getUsername() );
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

    public TenantTO createTenantGRP(TenantTO x){
        Response response = null;
        Keycloak keycloak = null;
        try {
            keycloak = openKeycloak();
            RealmResource realm = keycloak.realm( realmManaged );

            Optional<GroupRepresentation> grpOpt = findGrpByName( realm , x.getTenantId() );
            if ( grpOpt.isPresent() ){
                x.setIdpGrpId( grpOpt.get().getId() );
                return x;
            }
            GroupRepresentation grp =  KeycloakUtils.initGrp(x.getTenantId());
            response =   keycloak.realm( realmManaged ).groups().add( grp );
            if (response.getStatus() == 201) {
                String location = response.getHeaderString("Location");
                String grpId = location.substring(location.lastIndexOf("/") + 1);
                x.setIdpGrpId( grpId  );
                return x;

            } else {
                System.out.println( response.getEntity() );
                throw new RuntimeException( " unable to create user ok keycloak: " + x.getTenantId() );
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



}
