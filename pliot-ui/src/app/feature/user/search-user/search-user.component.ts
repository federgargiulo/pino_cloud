import { Component, OnInit } from '@angular/core';
import { TenantServices } from '../../../service/tenant.service';
import { UserService } from '../../../service/user.service';
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';

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
@Component({
  selector: 'app-search-user',
  standalone: false,
  templateUrl: './search-user.component.html',
  styleUrl: './search-user.component.scss',
})
export class SearchUserComponent implements OnInit {

  displayedColumns: string[] = ['select', 'userId', 'firstName', 'lastName', 'email', 'idpId'];
  data = Object.assign( ELEMENT_DATA);
  dataSource = new MatTableDataSource<usersList>(ELEMENT_DATA);
  selection = new SelectionModel<usersList>(true, []);

  tenants: tenantList[] = [
    { tenantId: 'tenant1', tenantName: 'Mock Tenant 1' },
    { tenantId: 'tenant2', tenantName: 'Mock Tenant 2' }
  ];

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

    // Confirm deletion with user
    if (!confirm(`Are you sure you want to delete ${this.selection.selected.length} selected user(s)?`)) {
      return;
    }

    // Create a copy of current data to modify
    const currentData = [...this.dataSource.data];

    this.selection.selected.forEach(user => {
      const index = currentData.findIndex(u =>
        u.userId === user.userId  // More reliable than reference comparison
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

  constructor(private tenantServices: TenantServices,
    private userService: UserService) { }

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
}

