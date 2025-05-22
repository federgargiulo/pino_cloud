import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpProviderService } from './http-provider.service';

const isMockEnabled = false;

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
    if (isMockEnabled) {
          const mockDetail: UserDashboardDetail = {
            id: model,
            userId: 'mock-user-id',
            descr: 'Mock dashboard per utente',
            title: 'Mock Dashboard Title',
            configuration: '{"widgets": ["chart", "table"]}'
          };
          return of({ body: mockDetail });
    } else {
      console.info( "Service is calling " + httpLink.baseDashboard + " With data " + model )
      alert( httpLink.baseDashboard + '/' + model );
      return this.webApiService.get(httpLink.baseDashboard + '/' + model);
    }
  }

  public addUserDashboard(model: any): Observable<any> {
    if (isMockEnabled) {
      const mockCreated = { ...model, id: 'mock-created-id' };
      return of({ body: mockCreated });
    } else {
      console.info( "Service is calling " + httpLink.baseDashboard + " With data " + model )
      return this.webApiService.post( httpLink.baseDashboard , model );
    }
  }

  public getUserDashboards(): Observable<any> {
    if (isMockEnabled) {
      const mockList: UserDashboardDetail[] = [
        {
          id: 'dash-1',
          userId: 'mock-user-1',
          descr: 'Mock descrizione dashboard 1',
          title: 'Dashboard Mock 1',
          configuration: '{"widgets": ["chart"]}'
        },
        {
          id: 'dash-2',
          userId: 'mock-user-2',
          descr: 'Mock descrizione dashboard 2',
          title: 'Dashboard Mock 2',
          configuration: '{"widgets": ["table"]}'
        }
      ];
      return of({ body: mockList });
    } else {
      console.info( "Service is calling " + httpLink.baseDashboard  )
      return this.webApiService.get(httpLink.baseDashboard );
    }
  }

  public deleteUserDashboard(model: any): Observable<any> {
    if (isMockEnabled) {
      console.log("Mock delete dashboard ID:", model);
      return of({ status: 204 });
    }else{
      console.info( "Service is calling " + httpLink.baseDashboard + " With data " + model )
      return this.webApiService.delete( httpLink.baseDashboard+"/"+ model );
    }
  }

  public updateUserDashboard(model: any): Observable<any> {
    if (isMockEnabled) {
      const mockUpdated = { ...model };
      return of({ body: mockUpdated });
    } else{
      console.info( "Service is calling " + httpLink.baseDashboard + " With data " + model )
      return this.webApiService.put( httpLink.baseDashboard+"/"+ model.id , model );
    }
  }

}
