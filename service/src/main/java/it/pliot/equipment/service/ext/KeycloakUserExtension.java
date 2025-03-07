package it.pliot.equipment.service.ext;

import jakarta.annotation.PostConstruct;
import org.keycloak.admin.client.Keycloak;

import org.keycloak.admin.client.resource.RealmResource;

import org.springframework.stereotype.Component;

@Component
public class KeycloakUserExtension {

    private RealmResource realm;

     
    public void postConstruct() {

        Keycloak keycloak = Keycloak.getInstance(
                "http://localhost:8080",
                "master",
                "admin",
                "password",
                "admin-cli");

        this.realm = keycloak.realm("master");

    }


    public void createUser(){

    }


}
