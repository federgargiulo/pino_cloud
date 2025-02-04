import { Injectable } from '@angular/core';
import { Equipment } from '../equipment';

@Injectable({
  providedIn: 'root'
})
export class EquipmentService {

  url = 'http://localhost:8080/equipments';

  constructor() { }

   equipments: Equipment[] =[
        {
        equipmentId: "a",
        status: "s",
        name: "s",
        version: "string"
        },
       {
        equipmentId: "b",
        status: "string",
        name: "string",
        version: "string"
      },
      {
        equipmentId: "3b",
        status: "erewr",
        name: "strirng",
        version: "string"
      }
   ]

   async getAllEquipments(): Promise<Equipment[]> {
      const data = await fetch(this.url);
      return await data.json() ?? [];
  }
}
