import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpProviderService } from './http-provider.service';


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

  public getAllTenants(): Observable<any> {
    return this.webApiService.get(httpLink.getAllTenants );
  }


  public deleteTenantById(id: string): Observable<any> {
     console.info( "Service is calling" + httpLink.getTenantById + " With id: " + id )
     return this.webApiService.delete(httpLink.deleteTenantById + '/' + id);
  }

  public getTenantDetailById(model: any): Observable<any> {
    console.info( "Service is calling " + httpLink.getTenantDetailById + " With data " + model )
    return this.webApiService.get(httpLink.getTenantDetailById + '/' + model);
  }

  public addTenant(model: any): Observable<any> {
    console.info( "Service is calling " + httpLink.saveTenant + " With data " + model )
    return this.webApiService.post( httpLink.saveTenant , model );
  }

  public updateTenant( idobject:any ,model: any): Observable<any> {
    return this.webApiService.patch( httpLink.saveTenant + '/' + idobject , model );
  }

  public getTenantById(id: string): Observable<any> {
      console.info( "Service is calling getTenantById" + httpLink.getTenantById + " With id: " + id )
      return this.webApiService.get(httpLink.getTenantById + '/' + id);
    }

}
