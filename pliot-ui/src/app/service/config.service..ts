import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {

  DEV:string          = "localhost:4200";
  LOCAL_HOST:string   = "http://localhost:8080";
  LOCAL_IDP_URL:string   = "http://localhost:8180";
 // REMOTE_IDP_URL:string   = "https://pinocloud.duckdns.org:8081";
  REMOTE_IDP_URL:string   = "http://localhost:8180";
  BASE_URL: string        = '';
  SERVICE_URL : string    = this.LOCAL_HOST;

  config : any;
  constructor() {
    const configElement = document.getElementById('appConfig');
    if (configElement) {
      try {
        this.config = JSON.parse(configElement.textContent || '{}');    
      } catch (error) {
        console.error('❌ Errore nel parsing della configurazione:', error);
      }

      try {
        if (window.location.host.indexOf(this.DEV) >= 0) {
          this.SERVICE_URL = window.location.protocol + "////" + this.LOCAL_HOST;
        
        }
        else {
          this.SERVICE_URL = window.location.protocol + "////" + window.location.host;
         
        }
      } catch (err) {
        console.error(err)
      }

    }
  }

  getIDPUrl(){
     return this.config.idpUrl;
  }

  getClientId(){
    return this.config.clientId;
  }
  getRealm(){
    return this.config.realm;
  }

  getConfig(){
    return this.config;
  }
}
