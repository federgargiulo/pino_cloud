package it.pliot.equipment.service.ext;

import it.pliot.equipment.Const;
import org.keycloak.admin.client.resource.GroupResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeycloakGroupCmd {

    private static final Logger log = LoggerFactory.getLogger(KeycloakGroupCmd.class);

    private static  KeycloakGroupCmd INSTAMCE = new KeycloakGroupCmd();

    public static KeycloakGroupCmd getInstance(){return  INSTAMCE; }

    private KeycloakGroupCmd(){}

    public void createGroupIfNotExist( RealmResource realm , String groupId , String groupName ){
        GroupRepresentation grp = new GroupRepresentation() ;
        grp.setName( groupName );
        grp.setId( groupId );
        GroupResource grpk = realm.groups().group( Const.DEV_TENANT_ID );
        if ( grpk == null ) {
            realm.groups().add(grp);
            log.info( " created group id {} {} " ,new String[]{  grp.getId() , grp.getName()});
        }
    }
}
