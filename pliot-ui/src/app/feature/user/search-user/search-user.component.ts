import { Component, OnInit } from '@angular/core';
import { TenantServices } from '../../../service/tenant.service';
import { UserService } from '../../../service/user.service';

@Component({
  selector: 'app-search-user',
  standalone: false,
  templateUrl: './search-user.component.html',
  styleUrl: './search-user.component.css'
})
export class SearchUserComponent  implements OnInit {

  tenantList: any = [];

  usersList: any = [];


  selectedTenant: string = '';

  constructor(  private tenantServices: TenantServices , 
                private userService : UserService ) {}

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

      search(){

        console.log( "get users by tenant" )
        this.userService.getUserdByTenant( this.selectedTenant ).subscribe((data : any) => {
         console.log("Dati ricevuti dal server:", data)
          if (data != null && data.body != null) {
            var resultData = data.body;

            if (resultData) {
              this.usersList = resultData;
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

    deleteUser( userid: string , index : number ){
        alert( "delete user " + userid );
    }

    
   resetPassword( userid: string , index : string ){
      alert( "reset password user " + userid );
  }
}

