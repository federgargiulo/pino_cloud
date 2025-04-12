package it.pliot.equipment.service.edge.cmd;

import it.pliot.equipment.service.edge.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public abstract  class BaseHttpCmd {


    @Autowired
    private TokenManager tmaneger;

    @Value("${pliot.edge.client-id}")
    private String clientId;

    @Value("${pliot.edge.server-url}" )
    private String serverUrl;

    public TokenManager getTmaneger() {
        return tmaneger;
    }

    public void setTmaneger(TokenManager tmaneger) {
        this.tmaneger = tmaneger;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAccessToken(){
        return tmaneger.getAccessToken();
    }

}
