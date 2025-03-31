import { Component } from '@angular/core';
import { TenantServices } from '../../../service/tenant.service';
import { UserService } from '../../../service/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from 'express';
import { ActivatedRoute } from '@angular/router';
import { error } from 'console';
import { CommonService } from '../../../service/common.service';



@Component({
  selector: 'app-detail-user',
  standalone: false,
  templateUrl: './detail-user.component.html',
  styleUrl: './detail-user.component.css'
})
export class DetailUserComponent {

    userForm: FormGroup;
    tenantList: any = [];
    grpList: any = [];

    selectedTenant: string = '';

    constructor( private route: ActivatedRoute,
                 private fb: FormBuilder, private tenantServices: TenantServices ,
                 private userService : UserService ,
                 private commonServices: CommonService ) {

     this.userForm = this.fb.group({
       idpId: [''],
       userId: ['', [Validators.required]],
       firstName: ['', [Validators.required]],
       lastName: ['', [Validators.required]],
       email: ['', [Validators.required, Validators.email]],
       tenant: ['', [Validators.required]],
       password: ['', [Validators.required]],
      
       address: [''],
         phone: [''],
         gender: [''],
         usrGrp:  [[]]
     });
    }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { passwordMismatch: true };
}
    onSubmit(){
        const formValue = this.userForm.value;
        const userPayload = { ...formValue };
        delete userPayload.confirmPassword;

        this.userService.createUser( this.userForm.value ).subscribe(async data => {
          this.manageSuccessOnSave( data )
        },
        async error => {
          this.manageSuccessOnSave( error )
        });
    }

    isPersisted = false;
    successMessage: string = '';

    manageSuccessOnSave( data:any ){
      if (data != null && data.body != null) {
        var resultData = data.body;

        this.isPersisted = true;
        if (resultData != null && resultData.isSuccess) {
          this.successMessage = 'User creata con successo!';
        }

      }
    }

    setFormValues( resultData :any ){
       var x =  {
          idpId : resultData.idpId ,
          userId : resultData.userId,
          firstName: resultData.firstName, // Aggiunto title
          lastName: resultData.lastName,
          email: resultData.email,
          tenant: resultData.tenant,
          address: resultData.address || '',
           phone: resultData.phone || '',
           gender: resultData.gender || '',
            type: resultData.type || ''
        }
        this.userForm.setValue( x );

    }

    ngOnInit(): void {
        console.log( "init Tenant" )
        this.getAllTenants();
        this.laodAllGrp();
        this.route.paramMap.subscribe(params => {

          var userId = params.get('id') || '';
          if ( userId ! ){
            alert( "load user " + userId );
            this.userService.getUserById( userId ).subscribe({
              next: (data) => {
                alert( data.body.lastName  );
                this.setFormValues( data.body );
                this.isPersisted = true;
              },
              error: (err) => {
                console.error('Errore nel caricamento del dettaglio della dashboard', err);

              }
            })

          }


        })

      }

      async laodAllGrp(){
        console.log( "get all tenant" )
        this.commonServices.getAllGreoups().subscribe( (data: any )  => {
          console.log("Dati ricevuti dal server:", data)
          if (data != null && data.body != null) {
              this.grpList = data.body;           
          }
        });
      }

      async getAllTenants() {
          console.log( "get all tenant" )
          this.tenantServices.getAllTenants().subscribe((data : any) => {
           console.log("Dati ricevuti dal server:", data)
            if (data != null && data.body != null) {
              var resultData = data.body;

              if (resultData) {
                this.tenantList = resultData;
              }
            }
          },
          (error : any)=> {
              if (error) {
                if (error.status == 404) {
                  if(error.error && error.error.message){
                    this.tenantList = [];
                  }
                }
              }
            });
        }

}
