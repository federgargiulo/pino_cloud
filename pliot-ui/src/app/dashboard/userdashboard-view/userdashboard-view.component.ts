import { Component } from '@angular/core';
import { EquipmentServices } from '../../service/equipment.service';
import { UserDashboardService } from '../../service/user-dashboard.service';
import { Router,  ActivatedRoute  } from '@angular/router';
import { OnInit ,ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-userdashboard-view',
  standalone: false,
  templateUrl: './userdashboard-view.component.html',
  styleUrl: './userdashboard-view.component.css'
})

export class UserdashboardViewComponent implements OnInit {

  @ViewChild("dashboardForm")
  DashboardForm!: NgForm;

  isSubmitted: boolean = false;
  constructor(private router: Router,
              private route: ActivatedRoute,
              private equipmentServices: EquipmentServices,
              private userDashboardService: UserDashboardService ) { }

   equipment: any[] = [];
   isLoading: boolean = true;
   signalId : string ="";

   ngOnInit(): void {

       this.route.paramMap.subscribe(params => {
            var dashId = params.get('id') || ''; // Assicura che non sia null
            console.log("Dashboard ID ricevuto:",  dashId );
            alert( dashId );
            alert( " dashid " + dashId );
            this.userDashboardService.getUserDashboardById( dashId ).subscribe(
                {

                  next: (data) => {
                    alert( data );

                  },
                  error: (err) => {
                    console.error('Errore nel caricamento del dettaglio della dashboard', err);

                  }
                }
              )

            })

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

    addDashboard(isValid: any): void{
      this.isSubmitted = true;

      if (isValid) {
        console.info( " addEquipmentForm " + this.DashboardForm.name );
        var conf  = "[  signalId: " + this.signalId + " ] ";
        this.userDashboardService.addUserDashboard(
                {
                descr:  this.DashboardForm.name,
                configuration: conf ,
                } ).subscribe(async data => {
          if (data != null && data.body != null) {
            if (data != null && data.body != null) {
              var resultData = data.body;
              if (resultData != null && resultData.isSuccess) {

                setTimeout(() => {
                  this.router.navigate(['/']);
                }, 500);
              }
            }
          }
        },
          async error => {

            setTimeout(() => {
              this.router.navigate(['/']);
            }, 500);
          });
      }


    }

}

