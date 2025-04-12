import { inject, Injectable } from '@angular/core';
import { HttpProviderService } from './http-provider.service';
import { Observable } from 'rxjs';
import Keycloak from 'keycloak-js';

var version="";

var httpLink = {
  baseDashboard:  version + "/users",
  getUserById: version +  "/users",
  federate: version + "users/federateCurrentUser"
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
    public federateCurrentUser(): Observable<any> {
      console.info( "Service is calling " + httpLink.federate )
      return this.webApiService.get(httpLink.federate );
   }
  

  public createUser(usr: any): Observable<any> {
      console.info( "Service is calling " + httpLink.baseDashboard + " With data " + usr )
      return this.webApiService.post(httpLink.baseDashboard , usr );
  }


  public updateUser(usr: any): Observable<any> {
    console.info( "Service is calling " + httpLink.baseDashboard + " With data " + usr )
    return this.webApiService.patch(httpLink.baseDashboard + '/' + usr.idpId , usr );
  }

  public getUserById(userId: any): Observable<any> {
    console.info( "Service is calling getUserById " + httpLink.getUserById + " With data " + userId )
    return this.webApiService.get(httpLink.getUserById + '/' + userId );
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

  getCurrentUserGroups(): string[] {
    return this.keycloak.tokenParsed?.['groups'] || [];
  }

}
