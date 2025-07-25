import { Component } from '@angular/core';
import { EquipmentServices } from '../../service/equipment.service';
import { UserDashboardService } from '../../service/user-dashboard.service';
import { Router,  ActivatedRoute  } from '@angular/router';
import { OnInit ,ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';

@Component({
  selector: 'app-userdashboard-view',
  standalone: false,
  templateUrl: './userdashboard-view.component.html',
  styleUrl: './userdashboard-view.component.css'
})

export class UserdashboardViewComponent implements OnInit {

  dashBoardForm : FormGroup;
  successMessage: string = '';
  isEditMode: boolean = false;
  isSubmitted: boolean = false;
  constructor(private router: Router,
              private route: ActivatedRoute,
              private fb: FormBuilder,
              private equipmentServices: EquipmentServices,
              private userDashboardService: UserDashboardService ) {

                this.dashBoardForm = this.fb.group({
                  id : [''],
                  title: ['', Validators.required], // Aggiunto title
                  descr: ['', Validators.required],
                  configuration: [''],
                  shared: [false]  // Aggiunto desc
                });

  }
  isPersisted = false;
  equipment: any[] = [];
  isLoading: boolean = true;
  signalId : string ="";

   ngOnInit()  {
       const id = this.route.snapshot.paramMap.get('id');
       this.isEditMode = !!id;
       this.route.paramMap.subscribe(params => {
            var dashId = params.get('id') || '';

            if ( dashId ! ){
              this.isPersisted = true;
              this.userDashboardService.getUserDashboardById( dashId ).subscribe(
                {

                  next: (data) => {

                    this.setFormValues( data.body );
                    this.isPersisted = true;
                  },
                  error: (err) => {
                    console.error('Errore nel caricamento del dettaglio della dashboard', err);

                  }
                }
              )
            }


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

    setFormValues( resultData : any ){

      this.dashBoardForm.setValue( { id : resultData.id ,
        title: resultData.title, // Aggiunto title
        descr: resultData.descr,
         shared: resultData.shared === true || resultData.shared === 'true',
        configuration: resultData.configuration,
       } ) ;
    }

    manageSuccessOnSaveDashboard( data:any ){
      if (data != null && data.body != null) {
        var resultData = data.body;
        this.setFormValues( resultData );
        this.isPersisted = true;
        if (resultData != null && resultData.isSuccess) {
          this.successMessage = 'Dashboard creata con successo!';

        }

      }
    }

    manageErrorOnSaveDashboard( error:any ){
      setTimeout(() => {
        this.router.navigate(['/']);
      }, 500);
    }
    manageSave( ){
        this.userDashboardService.addUserDashboard( this.dashBoardForm.value ).subscribe(async data => {
        this.manageSuccessOnSaveDashboard( data )
      },
        async error => {
          this.manageErrorOnSaveDashboard( error )
      });
    }
    manageUpdate(){

      this.userDashboardService.updateUserDashboard( this.dashBoardForm.value ).subscribe(async data => {
        this.manageSuccessOnSaveDashboard( data )
      },
        async error => {
          this.manageErrorOnSaveDashboard( error )
      });
    }


    onSubmit() {

      if (this.dashBoardForm.valid) {
        if ( this.dashBoardForm.value.id ! )
          this.manageUpdate();
        else
          this.manageSave();

      }
    }

    updateJson(json: string) {

      this.dashBoardForm.controls['configuration'].setValue(json);
    }

onCancel() {
  // Se sei in un dialog:
  // this.dialogRef.close();

  // Altrimenti:
  this.router.navigate(['/']);
}
}

