import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpProviderService } from './http-provider.service';



var version="";

var httpLink = {
  baseDashboard:  version + "/userdashboards",
  
}

export interface UserDashboardDetail { // Anche questa deve essere esportata
  id: string;
  userId: string;
  descr: string;
  title: string ;
  configuration: string; 
}



@Injectable({
  providedIn: 'root'
})export class UserDashboardService {

  constructor(private webApiService: HttpProviderService   ) { }


 public getUserDashboardById(model: any): Observable<any> {
    console.info( "Service is calling " + httpLink.baseDashboard + " With data " + model )
    alert( httpLink.baseDashboard + '/' + model );
    return this.webApiService.get(httpLink.baseDashboard + '/' + model);
  }

  public addUserDashboard(model: any): Observable<any> {
    console.info( "Service is calling " + httpLink.baseDashboard + " With data " + model )
    return this.webApiService.post( httpLink.baseDashboard , model );
  }

  public getUserDashboards(): Observable<any> {
    console.info( "Service is calling " + httpLink.baseDashboard  )
    return this.webApiService.get(httpLink.baseDashboard );
  }

  public deleteUserDashboard(model: any): Observable<any> {
    console.info( "Service is calling " + httpLink.baseDashboard + " With data " + model )
    return this.webApiService.delete( httpLink.baseDashboard+"/"+ model );
  }

  public updateUserDashboard(model: any): Observable<any> {
    console.info( "Service is calling " + httpLink.baseDashboard + " With data " + model )
    return this.webApiService.put( httpLink.baseDashboard+"/"+ model.id , model );
  }

}
