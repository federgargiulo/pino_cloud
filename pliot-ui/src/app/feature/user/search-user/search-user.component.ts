import { Component, OnInit } from '@angular/core';
import { TenantServices } from '../../../service/tenant.service';
import { UserService } from '../../../service/user.service';
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';

const isMockEnabled = true;
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

const ELEMENT_DATA: usersList[] = [
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

interface Animal {
  name: string;
  sound: string;
}

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-search-user',
  standalone: false,
  templateUrl: './search-user.component.html',
  styleUrl: './search-user.component.scss'
})
export class SearchUserComponent implements OnInit {
  displayedColumns: string[] = ['select', 'userId', 'firstName', 'lastName', 'email', 'idpId'];
  data = Object.assign(ELEMENT_DATA);
  dataSource = new MatTableDataSource<usersList>(ELEMENT_DATA);
  selection = new SelectionModel<usersList>(true, []);
  hasSelection = false;
  hasSingleSelection = false;

  tenants: tenantList[] = [
    { tenantId: 'tenant1', tenantName: 'Mock Tenant 1' },
    { tenantId: 'tenant2', tenantName: 'Mock Tenant 2' }
  ];

  animalControl = new FormControl<Animal | null>(null, Validators.required);
  selectFormControl = new FormControl('', Validators.required);
  animals: Animal[] = [
    {name: 'Dog', sound: 'Woof!'},
    {name: 'Cat', sound: 'Meow!'},
    {name: 'Cow', sound: 'Moo!'},
    {name: 'Fox', sound: 'Wa-pa-pa-pa-pa-pa-pow!'},
  ];

  emailFormControl = new FormControl('', [Validators.required, Validators.email]);

  matcher = new MyErrorStateMatcher();

  constructor(
    private tenantServices: TenantServices,
    private userService: UserService,
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

  getAllTenants() {
    if (isMockEnabled) {
      // 👇 MOCK DATA
      this.tenantList
    } else {
      console.log("get all tenant")
      this.tenantServices.getAllTenants().subscribe((data: any) => {
        console.log("Dati ricevuti dal server:", data)
        if (data != null && data.body != null) {
          var resultData = data.body;

          if (resultData) {
            this.tenantList = resultData;
          }
        }
      },
        (error: any) => {
          if (error) {
            if (error.status == 404) {
              if (error.error && error.error.message) {
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
      this.usersList
    } else {
      console.log("get users by tenant")
      this.userService.getUserdByTenant(this.selectedTenant).subscribe((data: any) => {
        console.log("Dati ricevuti dal server:", data)
        if (data != null && data.body != null) {
          var resultData = data.body;

          if (resultData) {
            this.usersList = resultData;
          }
        }
      },
        (error: any) => {
          if (error) {
            if (error.status == 404) {
              if (error.error && error.error.message) {
                this.tenantList = [];
              }
            }
          }
        });
    }
  }

  deleteUser(userid: string, index: number) {
    alert("delete user " + userid);
  }

  resetPassword(userid: string, index: string) {
    alert("reset password user " + userid);
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
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

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  toggleAllRows() {
    if (this.isAllSelected()) {
      this.selection.clear();
      return;
    }

    this.selection.select(...this.dataSource.data);
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

