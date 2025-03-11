import { Injectable } from '@angular/core';
import { HttpProviderService } from './http-provider.service';
import { Observable } from 'rxjs';

var version="";

var httpLink = {
  baseDashboard:  version + "/users",
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private webApiService: HttpProviderService ) { }

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
}
