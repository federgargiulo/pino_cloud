package it.pliot.equipment.service.ext;

import it.pliot.equipment.io.EdgeTO;
import it.pliot.equipment.io.TenantTO;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.ProtocolMapperRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class KeycloakTenantExtension extends BaseKeycloakExtension {

    private static final Logger log = LoggerFactory.getLogger(KeycloakTenantExtension.class);


    public void alignKeycloakOnNewTenat(TenantTO x) {
        Response response = null;
        Keycloak keycloak = null;
        try {
            keycloak = openKeycloak();
            RealmResource realm = keycloak.realm(getRealmManaged() );
            createWebEdgeClient(x, realm);
            createApplicationClient(x, realm);

        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            if (keycloak != null) {
                try {
                    keycloak.close();
                } catch (Exception e) {
                    log.error(e.getMessage());
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
        String webclientid = getWebclientid(x);
        List<ClientRepresentation> elenco = realm.clients().findByClientId(webclientid);
        if (elenco != null && elenco.size() == 0)
            realm.clients().create(KeycloakUtils.createEdgeWebClient(webclientid, getServerBaseUrl() + "/*"));
    }

    private static String getWebclientid(TenantTO x) {
        return getWebclientid( x.getTenantId() );
    }
    private static String getWebclientid(String x) {
        return x + "_web_edge";
    }

    public Optional<GroupRepresentation> findGrpByName(RealmResource realm, String grpName) {
        Response res = null;
        try {
            List<GroupRepresentation> groups = realm
                    .groups()
                    .groups(grpName, 0, 10);
            return groups.isEmpty() ? Optional.empty() : Optional.of(groups.get(0));
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public Optional<String> findGrpIdByName(String grpName) {
        Keycloak keycloak = null;
        try {
            keycloak = openKeycloak();
            RealmResource realm = keycloak.realm(getRealmManaged());

            Optional<GroupRepresentation> groups = findGrpByName(realm, grpName);
            return groups.isEmpty() ? Optional.empty() : Optional.of(groups.get().getId());

        } finally {
            if (keycloak != null) {
                try {
                    keycloak.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public void alignKeycloakOnEdgeRegistration( EdgeTO x  ) {
        Response response = null;
        Keycloak keycloak = null;
        try {
            keycloak = openKeycloak();
            RealmResource realm = keycloak.realm(getRealmManaged() );
            addOrigin2AngularClient( x.getTenant() , realm , x.getEdgeUrl() );
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            if (keycloak != null) {
                try {
                    keycloak.close();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
  private void addOrigin2AngularClient(String tenant, RealmResource realm , String newOrigin ) {
        String angularClientId = getWebclientid(tenant);

        // 1. Trova il client per clientId
        List<ClientRepresentation> clients = realm.clients().findByClientId(angularClientId);
        if (clients == null || clients.isEmpty()) {
            throw new RuntimeException("Client non trovato: " + angularClientId);
        }

        ClientRepresentation clientRep = clients.get(0);
        String internalId = clientRep.getId(); // UUID del client

        ClientResource clientResource = realm.clients().get(internalId);

        // 2. Aggiorna i web origins
        List<String> currentOrigins = clientRep.getWebOrigins();
        if (currentOrigins == null) currentOrigins = new ArrayList<>();

        //String newOrigin = "https://app.miodominio.com";
        if (!currentOrigins.contains(newOrigin)) {
            currentOrigins.add(newOrigin);
            clientRep.setWebOrigins(currentOrigins);
            clientResource.update(clientRep);
        }


    }

}

