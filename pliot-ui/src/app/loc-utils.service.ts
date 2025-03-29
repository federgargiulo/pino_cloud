import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocUtilsService {


  DEV_CLIENT_ID = 'edge_app_dev';
  WEB_CLIENT_ID = 'edge_app';

  CLIENT_ID = this.DEV_CLIENT_ID;

  DEV:string          = "localhost:4200";
  LOCAL_HOST:string   = "http://localhost:8080";
  LOCAL_IDP_URL:string   = "http://localhost:8180";
 // REMOTE_IDP_URL:string   = "https://pinocloud.duckdns.org:8081";
  REMOTE_IDP_URL:string   = "http://localhost:8180";
  BASE_URL: string        = '';
  IDP_URL: string         = this.REMOTE_IDP_URL;
  REALM : string          = "pliot"
  SERVICE_URL : string    = this.LOCAL_HOST;

  constructor() {
    const configElement = document.getElementById('appConfig');
    if (configElement) {
      try {
        var config = JSON.parse(configElement.textContent || '{}');
        this.IDP_URL = config.idpUrl;
        this.REALM = config.realm;
        this.CLIENT_ID = config.clientId;

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
      return this.IDP_URL;
   }


   getClientId(){
    return this.CLIENT_ID;
 }
  getRealm(){
    return this.REALM;
  }
}
