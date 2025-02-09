import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpProviderService } from './http-provider.service';
import { Equipment } from './data/equipment';
 
var version="";

var httpLink = {
  getAllEquipment:  version + "/equipments",
  deleteEquipmentById:   version + "/equipments",
  getEquipmentDetailById:   version +  "/equipments",
  saveEquipment:  version +  "/equipments"
}

@Injectable({
  providedIn: 'root'
})export class EquipmentServices {

  constructor(private webApiService: HttpProviderService   ) { }
  
  public getAllEquipment(): Observable<any> {
    return this.webApiService.get(httpLink.getAllEquipment );
  }
  
  public deleteEquipmentById(model: any): Observable<any> {
    return this.webApiService.delete(httpLink.deleteEquipmentById + '/' + model );
  }
  
  public getEquipmentDetailById(model: any): Observable<any> {
    return this.webApiService.get(httpLink.getEquipmentDetailById + '/' + model);
  }
  
  public saveEquipment(model: any): Observable<any> {
    console.info( "Service is calling " + httpLink.saveEquipment + " With data " + model )
    return this.webApiService.post( httpLink.saveEquipment , model );
  }
  
  public updateEquipment( idobject:any ,model: any): Observable<any> {
    return this.webApiService.post( httpLink.saveEquipment + '/' + idobject , model );
  }
}
