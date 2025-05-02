package it.pliot.equipment.service.edge.cmd;

import it.pliot.equipment.GlobalConfig;
import it.pliot.equipment.service.edge.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public abstract  class BaseHttpCmd {


    @Autowired
    private TokenManager tmaneger;

    @Autowired
    private GlobalConfig config;

    public TokenManager getTmaneger() {
        return tmaneger;
    }

    public void setTmaneger(TokenManager tmaneger) {
        this.tmaneger = tmaneger;
    }

    public String getClientId() {
        return config.getClientId();
    }



    public String getServerUrl() {
        return config.getServerUrl();
    }


    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAccessToken(){
        return tmaneger.getAccessToken();
    }

}
