import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpProviderService } from './http-provider.service';


var version="";

var httpLink = {
  getAllSignals:  version + "/equipments",
  deleteSignalById:   version + "/equipments",
  getSignalDetailById:   version +  "/equipments",
  saveSignal:  version +  "/equipments",
  getSignalById: version +  "/equipments",
  getSignalDetail: version +  "/equipments",
  getSignalsByEquipmentId: version +  "/equipments"

}




@Injectable({
  providedIn: 'root'
})export class SignalServices {

  constructor(private webApiService: HttpProviderService   ) { }

  public getAllSignals(): Observable<any> {
    return this.webApiService.get(httpLink.getAllSignals );
  }


  public deleteSignalById(equipmentId: string, signalId: string): Observable<any> {
     console.info( "Service is calling" + httpLink.deleteSignalById + " With equipmentId: " + equipmentId +  'signalId:' + signalId )
     return this.webApiService.delete(httpLink.deleteSignalById + '/' + equipmentId + '/signals'+ '/' + signalId);
  }

  public getSignalDetailById(model: any): Observable<any> {
    console.info( "Service is calling " + httpLink.getSignalDetailById + " With data " + model )
    return this.webApiService.get(httpLink.getSignalDetailById + '/' + model);
  }

  public saveSignal(idobject:any, model: any): Observable<any> {
    console.info( "Service saveSignal is calling " + httpLink.saveSignal + " With data " + model )
    return this.webApiService.post( httpLink.saveSignal + '/' + idobject  + '/signals', model  );
  }

  public updateSignal( idobject:any ,model: any): Observable<any> {
    return this.webApiService.patch( httpLink.saveSignal + '/' + idobject + '/signals' , model );
  }

  public getSignalById(id: string): Observable<any> {
      console.info( "Service is calling getSignalById" + httpLink.getSignalById + " With id: " + id )
      return this.webApiService.get(httpLink.getSignalById + '/' + id);
    }

   public getSignalsByEquipmentId(id: string): Observable<any> {
      console.info( "Service is calling getSignalsByEquipmentId" + httpLink.getSignalsByEquipmentId + " With id: " + id )
      return this.webApiService.get(httpLink.getSignalsByEquipmentId + '/' + id + '/signals');
    }

}
