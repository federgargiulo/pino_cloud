import { Component } from '@angular/core';
import { EquipmentServices } from '../../service/equipment.service';
import { Router } from '@angular/router';
import { OnInit ,ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-userdashboard-view',
  standalone: false,
  templateUrl: './userdashboard-view.component.html',
  styleUrl: './userdashboard-view.component.css'
})

export class UserdashboardViewComponent implements OnInit {

  dashboardForm: DashboardForm = new DashboardForm();
    @ViewChild("dashboardForm")
    DashboardForm!: NgForm;

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
  
    addDashboardForm(isValid: any): void{
      
    }

}


export class DashboardForm {
  equipmentId: string="";
  name: string="";
  description: string="";
}