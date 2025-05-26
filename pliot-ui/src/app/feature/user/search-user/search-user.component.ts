import { Component, OnInit } from '@angular/core';
import { TenantServices } from '../../../service/tenant.service';
import { UserService } from '../../../service/user.service';
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';

interface tenantList {
  tenantId: string;
  tenantName: string;
}

export interface usersList {
  userId: string;
  firstName: string;
  lastName: string;
  email: string;
  idpId: string;
}


@Component({
  selector: 'app-search-user',
  standalone: false,
  templateUrl: './search-user.component.html',
  styleUrl: './search-user.component.scss'
})


export class SearchUserComponent implements OnInit {
  displayedColumns: string[] = ['select', 'userId', 'firstName', 'lastName', 'email', 'idpId'];

  dataSource = new MatTableDataSource<any>([]);
  selection = new SelectionModel<usersList>(true, []);
  hasSelection = false;
  hasSingleSelection = false;

  constructor(
    private tenantServices: TenantServices,
    private userService: UserService,
    private router: Router,
    private dialog: MatDialog
  ) {
    // Sottoscrizione ai cambiamenti della selezione
    this.selection.changed.subscribe(() => {
      this.hasSelection = this.selection.hasValue();
      this.hasSingleSelection = this.selection.selected.length === 1;
    });
  }

  ngOnInit(): void {
    console.log("init Tenant")
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


  deleteUser(userid: string, index: number) {
    alert("delete user " + userid);
  }

  resetPassword(userid: string, index: string) {
    alert("reset password user " + userid);
  }


   removeSelectedRows(): void {
      if (this.selection.isEmpty()) {
        console.warn('No rows selected for removal');
        return;
      }

      const dialogRef = this.dialog.open(ConfirmDialogComponent, {
        width: '400px',
        data: { message: `Sei sicuro di voler eliminare ${this.selection.selected.length} utente/i selezionato/i?` }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          // Create a copy of current data to modify
          const currentData = [...this.dataSource.data];

          this.selection.selected.forEach((user: any) => {
            const index = currentData.findIndex(u =>
              u.userId === user.userId
            );

            if (index > -1) {
              currentData.splice(index, 1);
            } else {
              console.warn(`User ${user.userId} not found in data source`);
            }
          });

          // Update data source
          this.dataSource.data = currentData;

          // Clear selection
          this.selection.clear();
        }
      });
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



  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: usersList): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.userId + 1}`;
  }

  tenantList: any = [];
  usersList: any = [];
  selectedTenant: string = '';
}

