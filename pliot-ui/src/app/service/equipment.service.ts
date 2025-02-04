import { Injectable, isDevMode } from '@angular/core';
import { Equipment } from '../data/equipment';

import { Location, LocationStrategy } from '@angular/common';


@Injectable({
  providedIn: 'root'
})
export class EquipmentService {

  static readonly BASE_DEV_URL  ="http://localhost:8080";
  static readonly BASE_PROD_URL ="http://localhost:8080";
  static readonly BASE_EQUIPMENT_RESOURCE ="/equipments";

  url = "" ;

  constructor( location: Location , locationStrateg : LocationStrategy) {

    console.log( "location path " + location.path() )
    console.log( "location Strateg " + locationStrateg.path() )
    console.log( "location getBaseHref " + locationStrateg.getBaseHref() )

    const baseUrl = isDevMode() ? EquipmentService.BASE_DEV_URL : EquipmentService.BASE_PROD_URL;

    this.url = baseUrl + EquipmentService.BASE_EQUIPMENT_RESOURCE;

    console.log( "Endpoint " + this.url );

  }



  async getAllEquipments(): Promise<Equipment[]> {
      const data = await fetch(this.url);
      return await data.json() ?? [];
  }
}
