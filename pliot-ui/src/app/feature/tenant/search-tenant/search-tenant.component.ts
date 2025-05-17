import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TenantServices } from '../../../service/tenant.service';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';

import { MatTableModule } from '@angular/material/table';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-search-tenant',
  standalone: false,
  templateUrl: './search-tenant.component.html',
  styleUrls: ['./search-tenant.component.scss']
})
export class SearchTenantComponent implements OnInit {

  tenantList: any[] = [];
  dataSource = new MatTableDataSource<any>([]);
  selection = new SelectionModel<any>(true, []);

  displayedColumns: string[] = [
    'select', 'tenantId', 'name', 'description', 'profile', 'address',
    'country', 'zipCode', 'state', 'email', 'createdDttm', 'updateDttm'
  ];

  constructor(
    private tenantServices: TenantServices,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getAllTenants();
  }

  getAllTenants(): void {
    this.tenantServices.getAllTenants().subscribe(
      (data: any) => {

        // ✅ Se è un array (mock), assegnalo direttamente
        if (Array.isArray(data)) {
          this.tenantList = data;
           this.dataSource.data = this.tenantList;
        } else if (data?.body) {
          // ✅ Se è una response con .body (chiamata reale), prendi body
          this.tenantList = data.body;
          this.dataSource.data = this.tenantList;
        } else {
          this.tenantList = [];
        }
      },
      (error: any) => {
        if (error?.status === 404 && error.error?.message) {
          this.tenantList = [];
          this.dataSource.data = [];
        }
      }
    );
  }

  editSelectedTenant() {
    if (this.selection.selected.length !== 1) {
      alert("Seleziona un solo tenant da modificare.");
      return;
    }
    const tenant = this.selection.selected[0];
    this.router.navigate(['/detail-tenant', tenant.tenantId]);
  }

  removeSelectedRows() {
    const selectedTenants = this.selection.selected;
    if (selectedTenants.length === 0) {
      alert("Seleziona almeno un tenant da eliminare.");
      return;
    }

    if (!confirm("Sei sicuro di voler eliminare i tenant selezionati?")) return;

    selectedTenants.forEach((tenant) => {
      this.tenantServices.deleteTenantById(tenant.tenantId).subscribe({
        next: () => {
          this.tenantList = this.tenantList.filter(t => t.tenantId !== tenant.tenantId);
          this.dataSource.data = [...this.tenantList];
          this.selection.clear();
        },
        error: (err) => {
          console.error(`Errore durante l'eliminazione di ${tenant.tenantId}:`, err);
        }
      });
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

  checkboxLabel(row?: any): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row`;
  }
}
