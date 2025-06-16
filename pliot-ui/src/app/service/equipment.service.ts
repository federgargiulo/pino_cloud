import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpProviderService } from './http-provider.service';
import { of } from 'rxjs'; // assicurati di importarlo

const isMockEnabled = false;

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

   if (isMockEnabled) {
       const mockData: any = [
         {
           equipmentId: 'mock-1',
           name: 'Mock Equipment A',
           version: '1.0',
           tenant: 'mockTenant',
           status: 'ACTIVE',
           createdDttm: new Date().toISOString(),
           updateDttm: new Date().toISOString()
         },
         {
           equipmentId: 'mock-2',
           name: 'Mock Equipment B',
           version: '1.2',
           tenant: 'mockTenant',
           status: 'INACTIVE',
           createdDttm: new Date().toISOString(),
           updateDttm: new Date().toISOString()
         }
       ];

       // ✅ Wrappiamo nel body per simulare la response HTTP vera
       return of({ body: mockData });
     }

     return this.webApiService.get(httpLink.getAllEquipment);
   }

 public saveEquipment(model: any): Observable<any> {
    if (isMockEnabled) {
      return of({
        body: {
          isSuccess: true,
          equipmentId: 'mock123',
          name: model.name,
          status: 'ACTIVE',
          tenant: 'mockTenant'
        }
      });
    }
    console.info( "Service is calling " + httpLink.saveEquipment + " With data " + model )
    // chiamata reale
    return this.webApiService.post(httpLink.saveEquipment, model);
  }



  public getEquipmentsByTenant(tenantId: string): Observable<any> {
    if (isMockEnabled) {
      const mockData: any = [
        {
          equipmentId: 'mock-1',
          name: 'Mock Equipment A',
          version: '1.0',
          tenant: tenantId || 'mockTenant',
          status: 'ACTIVE',
          createdDttm: new Date().toISOString(),
          updateDttm: new Date().toISOString()
        },
        {
          equipmentId: 'mock-2',
          name: 'Mock Equipment B',
          version: '2.0',
          tenant: tenantId || 'mockTenant',
          status: 'INACTIVE',
          createdDttm: new Date().toISOString(),
          updateDttm: new Date().toISOString()
        }
      ];
      return of({ body: mockData });
    }
    // Chiamata reale se mock disabilitato
      if (tenantId) {
        return this.webApiService.get(httpLink.getAllEquipment + '?tenantId=' + tenantId);
      } else {
        return this.getAllEquipment();
      }
    }



  public getAllEquipment4CurrentTenant(): Observable<any> {
    if (isMockEnabled) {
     const mockData: any = [
        {
          equipmentId: 'mock-tenant-1',
          name: 'Tenant Equipment Alpha',
          version: '1.0',
          tenant: 'mockTenant',
          status: 'ACTIVE',
          createdDttm: new Date().toISOString(),
          updateDttm: new Date().toISOString()
        },
        {
          equipmentId: 'mock-tenant-2',
          name: 'Tenant Equipment Beta',
          version: '1.2',
          tenant: 'mockTenant',
          status: 'INACTIVE',
          createdDttm: new Date().toISOString(),
          updateDttm: new Date().toISOString()
        }
      ];
      return of({ body: mockData });
    }

    return this.webApiService.get(httpLink.getEquipmentForCurrentTenant);
  }



  public deleteEquipmentById(id: string): Observable<any> {
     console.info( "Service is calling" + httpLink.deleteEquipmentById + " With id: " + id )
     return this.webApiService.delete(httpLink.deleteEquipmentById + '/' + id);
  }


  public updateEquipment(id: any, model: any): Observable<any> {
    if (isMockEnabled) {
      console.log("✅ Mock updateEquipment:", model);
      return of({ status: 200 });
    }
    return this.webApiService.patch(httpLink.saveEquipment + '/' + id, model);
  }


  public getEquipmentById(id: string): Observable<any> {
    if (isMockEnabled) {
      console.log("👉 MOCK getEquipmentById chiamato con ID:", id);

      const mockEquipment = {
        equipmentId: id,
        name: 'Mock Equipment ' + id,
        version: '1.0',
        tenant: 'mockTenant',
        status: 'ACTIVE',
        createdDttm: new Date().toISOString(),
        updateDttm: new Date().toISOString()
      };

      // ✅ Restituisci l’oggetto mock avvolto nel body (come fa il backend)
      return of({ body: mockEquipment });
    }

    console.info("Service is calling getEquipmentById " + httpLink.getEquipmentById + " With id: " + id);
    return this.webApiService.get(httpLink.getEquipmentById + '/' + id);
  }


 public createEquipmentPuller(id: string, model: any): Observable<any> {
   if (isMockEnabled) {
     model.pullerId = 'mock-created';
     return of(model);
   }
   return this.webApiService.post(httpLink.createEquipmentPuller + '/' + id + '/pullers', model);
 }



  public getPullersByEquipmentId(id: string): Observable<any> {
    if (isMockEnabled) {
      const mockPullers = [
        {
          pullerId: 'puller-1',
          equipmentId: id,
          tenant: 'mockTenant',
          url: 'http://mock.api/1',
          apiKey: 'abc123',
          intervalInSec: 60,
          nextExecutions: new Date().toISOString(),
          lastStart: new Date().toISOString(),
          lastEnd: new Date().toISOString(),
          lastExecutionReport: 'Success'
        }
      ];
      return of({ body: mockPullers });
    }
    console.info( "Service is calling getPullersByEquipmentId" + httpLink.getPullersByEquipmentId + " With id: " + id )
     return this.webApiService.get(httpLink.getPullersByEquipmentId + '/' + id + '/pullers');
  }




    public deletePullerById(equipmentId: string, pullerId: string): Observable<any> {
      if (isMockEnabled) {
        console.log("Mock deletePuller", pullerId);
        return of(null); // simulate 204
      }
      console.info( "Service is calling" + httpLink.deletePullerById + " With equipmentId: " + equipmentId +  'pullerId:' + pullerId )
      return this.webApiService.delete(httpLink.deletePullerById + '/' + equipmentId + '/pullers'+ '/' + pullerId);
    }


  public updatePuller(equipmentId: string, pullerId: string, model: any): Observable<any> {
    if (isMockEnabled) {
      console.log('Mock updatePuller', model);
      return of({ ...model, pullerId });
    }
    console.info( "Service is calling" + httpLink.updatePuller + " With equipmentId: " + equipmentId +  'pullerId:' + pullerId )
    return this.webApiService.patch(httpLink.updatePuller + '/' + equipmentId + '/pullers/' + pullerId, model);
  }




}
