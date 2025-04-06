import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpProviderService } from './http-provider.service';


var version="";

var httpLink = {
  getAllEquipment:  version + "/equipments",
  deleteEquipmentById:   version + "/equipments",
  getEquipmentDetailById:   version +  "/equipments",
  saveEquipment:  version +  "/equipments",
  getEquipmentById: version +  "/equipments",
  getEquipmentDetail: version +  "/equipments",
  getEquipmentForCurrentTenant: version + "/tenants/curr/equipments",
  createEquipmentPuller: version +  "/equipments",
  getPullersByEquipmentId: version +  "/equipments",
  deletePullerById: version +  "/equipments",
  updatePuller: version +  "/equipments"
}

export interface Equipment { // Anche questa deve essere esportata
  equipmentId: string,
  status: string,
  name: string,
  version: string,
  tenant: string,
  updateDttm: string,
  createdDttm: string
}

export interface EquipmentDetail { // Anche questa deve essere esportata
  equipmentId: string,
  name: string;
  status: string;
  version: string;
  createdDttm: string;
}

@Injectable({
  providedIn: 'root'
})export class EquipmentServices {

  constructor(private webApiService: HttpProviderService   ) { }

  public getAllEquipment(): Observable<any> {
    return this.webApiService.get(httpLink.getAllEquipment );
  }

  public getEquipmentsByTenant( tenantId: string  ): Observable<any> {
    if ( tenantId )
      return this.webApiService.get( httpLink.getAllEquipment + '?tenantId=' + tenantId );
    else
      return this.getAllEquipment();
  }

  public getAllEquipment4CurrentTenant(): Observable<any> {
    return this.webApiService.get(httpLink.getEquipmentForCurrentTenant );
  }

  public deleteEquipmentById(id: string): Observable<any> {
     console.info( "Service is calling" + httpLink.deleteEquipmentById + " With id: " + id )
     return this.webApiService.delete(httpLink.deleteEquipmentById + '/' + id);
  }

  public getEquipmentDetailById(model: any): Observable<any> {
    console.info( "Service is calling " + httpLink.getEquipmentDetailById + " With data " + model )
    return this.webApiService.get(httpLink.getEquipmentDetailById + '/' + model);
  }

  public saveEquipment(model: any): Observable<any> {
    console.info( "Service is calling " + httpLink.saveEquipment + " With data " + model )
    return this.webApiService.post( httpLink.saveEquipment , model );
  }

  public updateEquipment( idobject:any ,model: any): Observable<any> {
    return this.webApiService.patch( httpLink.saveEquipment + '/' + idobject , model );
  }

  public getEquipmentById(id: string): Observable<any> {
      console.info( "Service is calling getEquipmentById" + httpLink.getEquipmentById + " With id: " + id )
      return this.webApiService.get(httpLink.getEquipmentById + '/' + id);
    }


  public createEquipmentPuller(idobject:any, model: any): Observable<any> {
      console.info( "Service createEquipmentPuller is calling " + httpLink.createEquipmentPuller + " With data " + model )
      return this.webApiService.post( httpLink.createEquipmentPuller + '/' + idobject  + '/pullers', model  );
    }



  public getPullersByEquipmentId(id: string): Observable<any> {
    console.info( "Service is calling getPullersByEquipmentId" + httpLink.getPullersByEquipmentId + " With id: " + id )
    return this.webApiService.get(httpLink.getPullersByEquipmentId + '/' + id + '/pullers');
  }

  public deletePullerById(equipmentId: string, pullerId: string): Observable<any> {
       console.info( "Service is calling" + httpLink.deletePullerById + " With equipmentId: " + equipmentId +  'pullerId:' + pullerId )
       return this.webApiService.delete(httpLink.deletePullerById + '/' + equipmentId + '/pullers'+ '/' + pullerId);
    }

  public updatePuller(equipmentId: string, pullerId: string, model: any) {
    console.info( "Service is calling" + httpLink.updatePuller + " With equipmentId: " + equipmentId +  'pullerId:' + pullerId )
     return this.webApiService.patch(httpLink.updatePuller + '/' + equipmentId + '/pullers'+ '/' + pullerId, model );
  }
}
