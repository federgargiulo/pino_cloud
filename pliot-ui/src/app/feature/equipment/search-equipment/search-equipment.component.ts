import { Component, Input, OnInit, Type ,  ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Equipment, EquipmentServices } from '../../../service/equipment.service';


@Component({
  selector: 'app-search-equipment',
  standalone: false,
  templateUrl: './search-equipment.component.html',
  styleUrl: './search-equipment.component.css'
})


export class SearchEquipmentComponent implements OnInit  {

  equipmentList: any = [];


  constructor(  private equipmentServices: EquipmentServices, private router: Router) {}

  ngOnInit(): void {
    console.log( "init " )
    this.getAllEquipment();

  }



  async getAllEquipment() {
    console.log( "get all equipment" )
    this.equipmentServices.getAllEquipment().subscribe((data : any) => {
     console.log("Dati ricevuti dal server:", data)
      if (data != null && data.body != null) {
        var resultData = data.body;
        //console.log("Dati ricevuti dal server:", resultData)
        if (resultData) {
          this.equipmentList = resultData;
        }
      }
    },
    (error : any)=> {
        if (error) {
          if (error.status == 404) {
            if(error.error && error.error.message){
              this.equipmentList = [];
            }
          }
        }
      });
  }


  // **Eliminare un Equipment**
   deleteEquipment(id: string): void {
     if (!confirm("Sei sicuro di voler eliminare questo Equipment?")) {
       return;
     }

     this.equipmentServices.deleteEquipmentById(id).subscribe({
       next: () => {
         console.log(`Equipment con ID ${id} eliminato con successo!`);

         this.equipmentList = this.equipmentList.filter((e: Equipment) => e.equipmentId !== id);
         console.log(`this.equipmentList`, this.equipmentList);
       },
       error: (err) => {
         console.error("Errore durante l'eliminazione:", err);
       }
     });
   }


}
