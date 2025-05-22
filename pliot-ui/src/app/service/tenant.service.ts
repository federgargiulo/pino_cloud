import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpProviderService } from './http-provider.service';

const isMockEnabled = false;

var version="";

var httpLink = {
  getAllTenants:  version + "/tenants",
  deleteTenantById:   version + "/tenants",
  getTenantDetailById:   version +  "/tenants",
  saveTenant:  version +  "/tenants",
  getTenantById: version +  "/tenants",
  getTenantDetail: version +  "/tenants",
}

export interface Tenant { // Anche questa deve essere esportata
  tenantId: string,
  name: string,
  description: string,
  updateDttm: string,
  createdDttm: string,
  email: string,
  profile: string,
  country: string,
  state: string,
  zipCode: string,
  address: string

}

export interface TenantDetail { // Anche questa deve essere esportata
  tenantId: string,
  name: string;
  description: string;
  createdDttm: string;
  updateDttm: string;
  email: string;
    profile: string;
    country: string;
    state: string;
    zipCode: string;
    address: string;
}

@Injectable({
  providedIn: 'root'
})export class TenantServices {


  constructor(private webApiService: HttpProviderService   ) { }

   private mockTenants: Tenant[] = [
   {
       tenantId: '1',
       name: 'Mock Tenant Alpha',
       description: 'Mock tenant for testing',
       updateDttm: '2025-05-01T12:34:00',
       createdDttm: '2025-04-01T09:00:00',
       email: 'alpha@example.com',
       profile: 'ADMIN',
       country: 'IT',
       state: 'RM',
       zipCode: '00100',
       address: 'Via Roma 1'
   },
   {
       tenantId: '2',
       name: 'Mock Tenant Beta',
       description: 'Another mock tenant',
       updateDttm: '2025-05-10T08:00:00',
       createdDttm: '2025-04-05T10:00:00',
       email: 'beta@example.com',
       profile: 'USER',
       country: 'IT',
       state: 'MI',
       zipCode: '20100',
       address: 'Via Milano 2'
   }
  ];

   public getAllTenants(): Observable<any> {
     if (isMockEnabled) {
       console.info("Returning mocked tenant list");
       return of(this.mockTenants);
     } else {
       return this.webApiService.get(httpLink.getAllTenants);
     }
   }


   public deleteTenantById(id: string): Observable<any> {
      if (isMockEnabled) {
        console.info(`Mock deleting tenant with id ${id}`);
        this.mockTenants = this.mockTenants.filter(t => t.tenantId !== id);
        return of({ success: true });
      } else {
        console.info( "Service is calling" + httpLink.getTenantById + " With id: " + id )
        return this.webApiService.delete(httpLink.deleteTenantById + '/' + id);
       }
    }

  public getTenantDetailById(model: any): Observable<any> {
    if (isMockEnabled) {
          console.info(`Mock fetching tenant detail for id ${model}`);
          const tenant = this.mockTenants.find(t => t.tenantId === model);
          return of(tenant);
        }
    console.info( "Service is calling " + httpLink.getTenantDetailById + " With data " + model )
    return this.webApiService.get(httpLink.getTenantDetailById + '/' + model);
  }

  public addTenant(model: Tenant): Observable<any> {
      if (isMockEnabled) {
        console.info("Mock adding tenant", model);
        this.mockTenants.push(model);
        return of(model);
      }else {
        console.info( "Service is calling " + httpLink.saveTenant + " With data " + model )
        return this.webApiService.post(httpLink.saveTenant, model);
      }
    }


    public updateTenant( idobject:any ,model: any): Observable<any> {
      if (isMockEnabled) {
          console.info(`Mock updating tenant with id ${idobject}`);
          this.mockTenants = this.mockTenants.map(t =>
          t.tenantId === idobject ? { ...t, ...model } : t
        );
        return of(model);
      } else {
        return this.webApiService.patch(httpLink.saveTenant + '/' + idobject, model);
      }
    }


   public getTenantById(id: string): Observable<any> {
        if (isMockEnabled) {
          const tenant = this.mockTenants.find(t => t.tenantId === id);
          return of(tenant);
        } else{
          console.info( "Service is calling getTenantById" + httpLink.getTenantById + " With id: " + id )
          return this.webApiService.get(httpLink.getTenantById + '/' + id);
        }
      }


}
