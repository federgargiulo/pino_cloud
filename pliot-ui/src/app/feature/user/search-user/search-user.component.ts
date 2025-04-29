import { Component, OnInit } from '@angular/core';
import { TenantServices } from '../../../service/tenant.service';
import { UserService } from '../../../service/user.service';
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-user',
  standalone: false,
  templateUrl: './search-user.component.html',
  styleUrl: './search-user.component.scss'
})


export class SearchUserComponent implements OnInit {

  tenantList: any = [];
  usersList: any = [];

  selectedTenant: string = '';

  displayedColumns: string[] = ['select', 'userId', 'firstName', 'lastName', 'email', 'idpId'];
  selection = new SelectionModel<any>(true, []);
  dataSource = new MatTableDataSource<any>([]);

  constructor(private tenantServices: TenantServices,
              private userService: UserService,
              private router: Router ) {}


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
        alert ( " search " +  this.selectedTenant );
        console.log( "get users by tenant" )
        this.userService.getUserdByTenant( this.selectedTenant ).subscribe((data : any) => {
         console.log("Dati ricevuti dal server:", data)
          if (data != null && data.body != null) {
            var resultData = data.body;

            if (resultData) {
              this.usersList = resultData;

              this.dataSource.data = resultData;
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

   refreshUsers(): void {
      this.search(); // richiama la search con il tenant selezionato
    }

    editSelectedUser() {
      if (this.selection.selected.length !== 1) {
        alert("Seleziona un solo utente da modificare.");
        return;
      }
      const user = this.selection.selected[0];
      this.router.navigate(['/detail-user', user.idpId]);
    }

  toggleAllRows() {
    if (this.isAllSelected()) {
      this.selection.clear();
      return;
    }
    this.selection.select(...this.dataSource.data);
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  checkboxLabel(row?: any): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row`;
  }

  removeSelectedRows() {
    const selectedUsers = this.selection.selected;
    console.log('Users selezionati da rimuovere:', selectedUsers);
    // Qui puoi eventualmente implementare la logica di delete multiplo
  }

}

