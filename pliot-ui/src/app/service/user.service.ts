import { inject, Injectable } from '@angular/core';
import { HttpProviderService } from './http-provider.service';
import { Observable } from 'rxjs';
import Keycloak from 'keycloak-js';

var version="";

var httpLink = {
  baseDashboard:  version + "/users",
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  keycloak:any;

  constructor(private webApiService: HttpProviderService ) {
    this.keycloak = inject(Keycloak);
   }

  public getUserdByTenant(tenant: string): Observable<any> {
      console.info( "Service is calling " + httpLink.baseDashboard + " With data " + tenant )
      return this.webApiService.get(httpLink.baseDashboard + '?tenant=' + tenant );
    }

  
  public createUser(usr: any): Observable<any> {
      console.info( "Service is calling " + httpLink.baseDashboard + " With data " + usr )
      return this.webApiService.post(httpLink.baseDashboard , usr );
  }

  
  public updateUser(usr: any): Observable<any> {
    console.info( "Service is calling " + httpLink.baseDashboard + " With data " + usr )
    return this.webApiService.put(httpLink.baseDashboard + '/' + usr.id , usr );
  }

  public getUserById(id: any): Observable<any> {
    console.info( "Service is calling " + httpLink.baseDashboard + " With data " + id )
    return this.webApiService.get(httpLink.baseDashboard + '/' + id );
  }
   
  getUsername(): string | null {
    
    const keycloak = inject(Keycloak);
    const token = keycloak.token;

    return keycloak.tokenParsed?.['preferred_username'] || null;
  }
  
  getGetJWTAttribute( x: string ):string {
    return this.keycloak.tokenParsed?.[ x ] || null;
  }

  getCurrentUserName(): string | null { 
    return this.getGetJWTAttribute( 'preferred_username' );
  }

  getCurrentLastName(): string | null { 
    return this.getGetJWTAttribute( 'family_name' );
  }
  getCurrentFirstName(): string | null { 
    return this.getGetJWTAttribute( 'given_name' );
  }
  getCurrentUserId(): string | null { 
    return this.getGetJWTAttribute( 'sub' );
  }
  getCurrentUserEDmail(): string | null { 
    return this.getGetJWTAttribute( 'email' );
  }
 
}
