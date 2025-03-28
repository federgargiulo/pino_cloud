import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocUtilsService {


  DEV_CLIENT_ID = 'edge_app_dev';
  WEB_CLIENT_ID = 'edge_app';
  
  CLIENT_ID = this.WEB_CLIENT_ID;

  DEV:string          = "localhost:4200";
  LOCAL_HOST:string   = "http://localhost:8080";
  LOCAL_IDP_URL:string   = "http://localhost:8180";
  REMOTE_IDP_URL:string   = "https://pinocloud.duckdns.org:8081";
  BASE_URL: string        = '';
  IDP_URL: string         = this.REMOTE_IDP_URL;
  SERVICE_URL : string    = this.LOCAL_HOST;
 
  constructor() {

    try{
          if ( window.location.host.indexOf( this.DEV ) >= 0 ){
            this.SERVICE_URL = window.location.protocol + "////" + this.LOCAL_HOST;
            this.CLIENT_ID = this.DEV_CLIENT_ID; 
          }
         else{
             this.SERVICE_URL = window.location.protocol + "////" + window.location.host;
             this.CLIENT_ID = this.WEB_CLIENT_ID; 
          }
    }catch( err ){
        console.error( err )        
    }
   
   }

   getIDPUrl(){
      return this.IDP_URL;
   }
   
   
   getClientId(){
    return this.CLIENT_ID;
 }
  getRealm(){
    return 'pliot_dev';
  }
}
