import { Component, OnInit } from '@angular/core';
import { TenantServices } from '../../../service/tenant.service';
import { UserService } from '../../../service/user.service';
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
import { EditUserDialogComponent } from './edit-user-dialog/edit-user-dialog.component';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';

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
    private dialog: MatDialog,
    private snackBar: MatSnackBar
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
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '400px',
      data: { message: `Sei sicuro di voler eliminare l'utente ${userid}?` }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.userService.deleteUser(userid).subscribe({
          next: () => {
            this.snackBar.open('Utente eliminato con successo', 'Chiudi', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top'
            });
            this.search();
          },
          error: (error: HttpErrorResponse) => {
            this.snackBar.open('Errore durante l\'eliminazione dell\'utente', 'Chiudi', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top'
            });
          }
        });
      }
    });
  }

  resetPassword(userid: string, index: string) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '400px',
      data: { message: `Sei sicuro di voler resettare la password dell'utente ${userid}?` }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // Qui puoi aggiungere la logica per resettare la password
        this.snackBar.open('Password resettata con successo', 'Chiudi', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top'
        });
      }
    });
  }


   removeSelectedRows(): void {
      if (this.selection.isEmpty()) {
        this.snackBar.open('Seleziona almeno un utente da eliminare', 'Chiudi', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top'
        });
        return;
      }

      const dialogRef = this.dialog.open(ConfirmDialogComponent, {
        width: '400px',
        data: { message: `Sei sicuro di voler eliminare ${this.selection.selected.length} utente/i selezionato/i?` }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          const currentData = [...this.dataSource.data];
          let successCount = 0;
          let errorCount = 0;

          this.selection.selected.forEach((user: any) => {
            this.userService.deleteUser(user.idpId).subscribe({
              next: () => {
                const index = currentData.findIndex(u => u.userId === user.userId);
                if (index > -1) {
                  currentData.splice(index, 1);
                  successCount++;
                }

                if (successCount + errorCount === this.selection.selected.length) {
                  this.dataSource.data = currentData;
                  this.selection.clear();

                  if (successCount > 0) {
                    this.snackBar.open(`${successCount} utente/i eliminato/i con successo`, 'Chiudi', {
                      duration: 3000,
                      horizontalPosition: 'end',
                      verticalPosition: 'top'
                    });
                  }
                  if (errorCount > 0) {
                    this.snackBar.open(`Errore durante l'eliminazione di ${errorCount} utente/i`, 'Chiudi', {
                      duration: 3000,
                      horizontalPosition: 'end',
                      verticalPosition: 'top'
                    });
                  }
                }
              },
              error: (error: HttpErrorResponse) => {
                console.error(`Errore durante l'eliminazione dell'utente ${user.idpId}:`, error);
                errorCount++;

                if (successCount + errorCount === this.selection.selected.length) {
                  this.dataSource.data = currentData;
                  this.selection.clear();

                  if (successCount > 0) {
                    this.snackBar.open(`${successCount} utente/i eliminato/i con successo`, 'Chiudi', {
                      duration: 3000,
                      horizontalPosition: 'end',
                      verticalPosition: 'top'
                    });
                  }
                  if (errorCount > 0) {
                    this.snackBar.open(`Errore durante l'eliminazione di ${errorCount} utente/i`, 'Chiudi', {
                      duration: 3000,
                      horizontalPosition: 'end',
                      verticalPosition: 'top'
                    });
                  }
                }
              }
            });
          });
        }
      });
    }

   refreshUsers(): void {
      this.search(); // richiama la search con il tenant selezionato
    }

    editSelectedUser() {
      if (this.selection.selected.length !== 1) {
        this.snackBar.open('Seleziona un solo utente da modificare', 'Chiudi', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top'
        });
        return;
      }

      const selectedUser = this.selection.selected[0];
      const dialogRef = this.dialog.open(EditUserDialogComponent, {
        data: { user: selectedUser },
        panelClass: 'equipment-edit-dialog'
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.userService.updateUser(result).subscribe({
            next: () => {
              this.snackBar.open('Utente modificato con successo', 'Chiudi', {
                duration: 3000,
                horizontalPosition: 'end',
                verticalPosition: 'top'
              });
              this.search(); // Ricarica la lista degli utenti
            },
            error: (error: HttpErrorResponse) => {
              this.snackBar.open('Errore durante la modifica dell\'utente', 'Chiudi', {
                duration: 3000,
                horizontalPosition: 'end',
                verticalPosition: 'top'
              });
            }
          });
        }
      });
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

