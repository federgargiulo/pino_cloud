import { Component } from '@angular/core';
import { TenantServices } from '../../../service/tenant.service';
import { UserService } from '../../../service/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-detail-user',
  standalone: false,
  templateUrl: './detail-user.component.html',
  styleUrl: './detail-user.component.css'
})
export class DetailUserComponent {

    userForm: FormGroup;
    tenantList: any = [];
    
    selectedTenant: string = '';
  
    constructor( private fb: FormBuilder, private tenantServices: TenantServices , 
                  private userService : UserService ) {
      
      this.userForm = this.fb.group({
        user_pk: [''],
        userId:  ['' ,  [Validators.required ] ],
        firstName:  ['' , [Validators.required ] ],  
        lastName:  ['' , [Validators.required ] ],           
        email: ['', [Validators.required, Validators.email]],
        tenantId:['' , [Validators.required]]
       
      });
    }

    onSubmit(){
      alert( "on submit ");
    
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
          this.successMessage = 'Dashboard creata con successo!';   
        }
    
      }
    }
    
    ngOnInit(): void {
        console.log( "init Tenant" )
        this.getAllTenants();
  
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
