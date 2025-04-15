import { Component, OnInit } from '@angular/core';
import { TenantServices } from '../../../service/tenant.service';
import { UserService } from '../../../service/user.service';
const isMockEnabled = true;
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



      getAllTenants() {
          if (isMockEnabled) {
            // 👇 MOCK DATA
            this.tenantList = [
              { tenantId: 'tenant1', name: 'Mock Tenant 1' },
              { tenantId: 'tenant2', name: 'Mock Tenant 2' }
            ];
          } else {
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




search() {
    if (isMockEnabled) {
      // 👇 MOCK DATA
      this.usersList = [
        {
          userId: 'u1',
          firstName: 'Mario',
          lastName: 'Rossi',
          email: 'mario@example.com',
          idpId: 'idp1'
        },
        {
          userId: 'u2',
          firstName: 'Luca',
          lastName: 'Bianchi',
          email: 'luca@example.com',
          idpId: 'idp2'
        }
      ];
    } else {
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
  }



    deleteUser( userid: string , index : number ){
        alert( "delete user " + userid );
    }


   resetPassword( userid: string , index : string ){
      alert( "reset password user " + userid );
  }
}

