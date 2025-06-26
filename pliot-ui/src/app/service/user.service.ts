import { inject, Injectable } from '@angular/core';
import { HttpProviderService } from './http-provider.service';
import { Observable, of } from 'rxjs';
import Keycloak from 'keycloak-js';

const isMockEnabled = false;

var version="";

var httpLink = {
  baseDashboard:  version + "/users",
  getUserById: version +  "/users",
  federate: version + "users/federate"
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

      if (isMockEnabled) {
        const mockUsers = [
          {
            idpId: 'user-1',
            username: 'jdoe',
            email: 'jdoe@example.com',
            firstName: 'John',
            lastName: 'Doe',
            tenant: tenant,
            groups: ['admin'],
            createdDttm: new Date().toISOString()
          },
          {
            idpId: 'user-2',
            username: 'asmith',
            email: 'asmith@example.com',
            firstName: 'Alice',
            lastName: 'Smith',
            tenant: tenant,
            groups: ['user'],
            createdDttm: new Date().toISOString()
          }
        ];
        return of({ body: mockUsers });
      } else {
        console.info( "Service is calling " + httpLink.baseDashboard + " With data " + tenant )
        return this.webApiService.get(httpLink.baseDashboard + '?tenant=' + tenant );
      }
    }
    public federateCurrentUser(): Observable<any> {
      if (isMockEnabled) {
        return of({ body: { isSuccess: true, federated: true } });
      } else {
        console.info( "Service is calling " + httpLink.federate )
        return this.webApiService.post(httpLink.federate , {} );
      }
   }


  public createUser(usr: any): Observable<any> {
      if (isMockEnabled) {
        return of({ body: { isSuccess: true, userId: 'mock-created-user', ...usr } });
      } else {
        console.info( "Service is calling " + httpLink.baseDashboard + " With data " + usr )
        return this.webApiService.post(httpLink.baseDashboard , usr );
      }
  }


  public updateUser(usr: any): Observable<any> {
    if (isMockEnabled) {
       return of({ body: { isSuccess: true, updatedUser: usr } });
    } else {
       console.info( "Service is calling " + httpLink.baseDashboard + " With data " + usr )
       return this.webApiService.patch(httpLink.baseDashboard + '/' + usr.idpId , usr );
     }
  }

  public getUserById(userId: any): Observable<any> {
    if (isMockEnabled) {
      const mockUser = {
        idpId: userId,
        username: 'mockuser',
        email: 'mockuser@example.com',
        firstName: 'Mock',
        lastName: 'User',
        tenant: 'mockTenant',
        groups: ['mockGroup']
      };
      return of({ body: mockUser });
    } else {
        console.info( "Service is calling getUserById " + httpLink.getUserById + " With data " + userId )
        return this.webApiService.get(httpLink.getUserById + '/' + userId );
    }
  }

  public deleteUser(userId: string): Observable<any> {
    if (isMockEnabled) {
      console.info(`Mock deleting user with id ${userId}`);
      return of({ success: true });
    } else {
      console.info("Service is calling delete user with id: " + userId);
      return this.webApiService.delete(httpLink.baseDashboard + '/' + userId);
    }
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

  getCurrentUserTenant(): string | null {
    return this.keycloak.tokenParsed?.['tenant'] || null;
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

  hasRole( role:string ): boolean{
    return this.hasRoleImpl( this.keycloak.tokenParsed , role );
  }
  hasRoleImpl(token: any, role: string): boolean {

    if (!token || !token.realm_access || !Array.isArray(token.realm_access.roles)) {
      return false;
    }
    return token.realm_access.roles.includes(role);
  }

  public getCurrentUserFromBackend(): Observable<any> {
    const userId = this.getCurrentUserId(); // usa il 'sub' dal token
    if (userId) {
      return this.getUserById(userId);
    } else {
      console.warn('Impossibile ottenere userId dal token');
      return of(null);
    }
  }

}
