import { Component } from '@angular/core';
import { EquipmentServices } from '../../service/equipment.service';
import { Router } from '@angular/router';
import { OnInit } from '@angular/core';
@Component({
  selector: 'app-userdashboard-view',
  standalone: false,
  templateUrl: './userdashboard-view.component.html',
  styleUrl: './userdashboard-view.component.css'
})

export class UserdashboardViewComponent implements OnInit {
   constructor(private router: Router, private equipmentServices: EquipmentServices ) { }

   equipment: any[] = [];
   isLoading: boolean = true;
   selectedEquipment : string ="";

   ngOnInit(): void { 
      this.equipmentServices.getAllEquipment4CurrentTenant().subscribe(
        {
          next: (data) => {
            this.equipment = data.body;
            this.isLoading = false;
          },
          error: (err) => {
            console.error('Errore nel caricamento dei dati', err);
            this.isLoading = false;
          }
        }
      )
    }


}


export class DashboardForm {
  equipmentId: string="";
  name: string="";
  description: string="";
}