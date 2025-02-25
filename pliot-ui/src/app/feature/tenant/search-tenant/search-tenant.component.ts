import { Component, Input, OnInit, Type ,  ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Tenant, TenantServices } from '../../../service/tenant.service';


@Component({
  selector: 'app-search-tenant',
  standalone: false,
  templateUrl: './search-tenant.component.html',
  styleUrl: './search-tenant.component.css'
})
export class SearchTenantComponent {

 tenantList: any = [];


  constructor(  private tenantServices: TenantServices, private router: Router) {}

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
          //console.log("Dati ricevuti dal server:", resultData)
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

    // **Eliminare un Tenant**
       deleteTenant(id: string, i:number): void {
                if (!confirm("Sei sicuro di voler eliminare questo Tenant?")) {
                  return;
                }

                this.tenantServices.deleteTenantById(id).subscribe({
                  next: () => {
                    console.log(`Tenant con ID ${id} eliminato con successo!`);

                   this.tenantList.splice(i,1);
                    console.log(`this.tenantList`, this.tenantList);
                  },
                  error: (err) => {
                    console.error("Errore durante l'eliminazione:", err);
                  }
                });
              }
}
