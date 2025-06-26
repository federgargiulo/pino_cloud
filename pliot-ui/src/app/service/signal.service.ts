import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpProviderService } from './http-provider.service';

const isMockEnabled = true;

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
      if (isMockEnabled) {
        const mockSignals = [
          {
            signalId: 'sig-1',
            name: 'Mock Signal A',
            description: 'Simulated Signal A',
            type: 'DIGITAL',
            status: 'ACTIVE',
            createdDttm: new Date().toISOString(),
            updateDttm: new Date().toISOString()
          },
          {
            signalId: 'sig-2',
            name: 'Mock Signal B',
            description: 'Simulated Signal B',
            type: 'ANALOG',
            status: 'INACTIVE',
            createdDttm: new Date().toISOString(),
            updateDttm: new Date().toISOString()
          }
        ];
        return of({ body: mockSignals });
      } else {
        return this.webApiService.get(httpLink.getAllSignals );
      }
  }


  public deleteSignalById(equipmentId: string, signalId: string): Observable<any> {
      if (isMockEnabled) {
        console.log(`Mock delete signal: ${signalId} from equipment ${equipmentId}`);
        return of(null); // simulate 204 No Content
      } else {
        console.info( "Service is calling" + httpLink.deleteSignalById + " With equipmentId: " + equipmentId +  'signalId:' + signalId )
        return this.webApiService.delete(httpLink.deleteSignalById + '/' + equipmentId + '/signals'+ '/' + signalId);
      }
  }

  public getSignalDetailById(model: any): Observable<any> {
      if (isMockEnabled) {
        const mockSignal = {
          signalId: model,
          name: 'Mock Signal Detail',
          description: 'Detailed info for mock signal',
          type: 'DIGITAL',
          status: 'ACTIVE',
          createdDttm: new Date().toISOString(),
          updateDttm: new Date().toISOString()
        };
        return of({ body: mockSignal });
      } else {
        console.info( "Service is calling " + httpLink.getSignalDetailById + " With data " + model )
        return this.webApiService.get(httpLink.getSignalDetailById + '/' + model);
      }
  }

  public saveSignal(idobject:any, model: any): Observable<any> {

    if (isMockEnabled) {
      return of({
        body: {
          isSuccess: true,
          signalId: 'mock-signal-id',
          ...model
        }
      });
    } else {
      console.info( "Service saveSignal is calling " + httpLink.saveSignal + " With data " + model )
      return this.webApiService.post( httpLink.saveSignal + '/' + idobject  + '/signals', model  );
    }
  }

  public updateSignal( equipmentId: string, signalId: string , model: any): Observable<any> {
      if (isMockEnabled) {
        console.log("Mock updateSignal", model);
        return of({ status: 200 });
      } else {
        return this.webApiService.patch( httpLink.saveSignal + '/' + equipmentId + '/signals'+ '/' + signalId, model);
      }
  }

  public getSignalById(id: string): Observable<any> {
      if (isMockEnabled) {
        const mockSignal = {
          signalId: id,
          name: 'Mock Signal',
          description: 'Signal mock data',
          type: 'DIGITAL',
          status: 'ACTIVE',
          createdDttm: new Date().toISOString(),
          updateDttm: new Date().toISOString()
        };
        return of({ body: mockSignal });
      } else {
        console.info( "Service is calling getSignalById" + httpLink.getSignalById + " With id: " + id )
        return this.webApiService.get(httpLink.getSignalById + '/' + id);
      }
    }

   public getSignalsByEquipmentId(id: string): Observable<any> {
      if (isMockEnabled) {
        const mockSignals = [
          {
            signalId: 'sig-1',
            equipmentId: id,
            name: 'Mock Signal X',
            description: 'Test signal X',
            type: 'DIGITAL',
            status: 'ACTIVE',
            createdDttm: new Date().toISOString(),
            updateDttm: new Date().toISOString()
          },
          {
            signalId: 'sig-2',
            equipmentId: id,
            name: 'Mock Signal Y',
            description: 'Test signal Y',
            type: 'ANALOG',
            status: 'INACTIVE',
            createdDttm: new Date().toISOString(),
            updateDttm: new Date().toISOString()
          }
        ];
        return of({ body: mockSignals });
      } else {
        console.info( " is calServiceling getSignalsByEquipmentId" + httpLink.getSignalsByEquipmentId + " With id: " + id )
        return this.webApiService.get(httpLink.getSignalsByEquipmentId + '/' + id + '/signals');
      }
    }

}
