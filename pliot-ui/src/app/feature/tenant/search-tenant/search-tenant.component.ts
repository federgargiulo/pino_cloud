import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TenantServices } from '../../../service/tenant.service';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialog } from '@angular/material/dialog';

import { MatTableModule } from '@angular/material/table';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

// Importa il componente dialog
import { TenantDialogComponent } from './tenant-dialog/tenant-dialog.component';
import { ConfirmDialogService } from './confirm-dialog/confirm-dialog.service';

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
  searchValue: string = '';

  displayedColumns: string[] = [
    'select', 'tenantId', 'name', 'description', 'profile', 'address',
    'country', 'zipCode', 'state', 'email', 'createdDttm', 'updateDttm'
  ];

  constructor(
    private tenantServices: TenantServices,
    private router: Router,
    private dialog: MatDialog,
    private confirmDialogService: ConfirmDialogService
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

  onInputChange() {
    // Se l'input è vuoto o contiene solo spazi, resetta il filtro
    if (!this.searchValue || this.searchValue.trim() === '') {
      this.dataSource.filter = '';
    }
  }

  applyFilter() {
    this.dataSource.filter = this.searchValue.trim().toLowerCase();
  }

  clearFilter(input: any) {
    this.searchValue = '';
    input.value = '';
    this.dataSource.filter = '';
  }

  createNewTenant() {
    const dialogRef = this.dialog.open(TenantDialogComponent, {
      width: '800px',
      data: { mode: 'create' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result && result.success) {
        // Aggiorna la lista dopo la creazione
        this.getAllTenants();
        this.confirmDialogService.showSuccess('Tenant creato con successo!');
      }
    });
  }

  editSelectedTenant() {
    if (this.selection.selected.length !== 1) {
      this.confirmDialogService.showInfo(
        'Informazione',
        'Seleziona un solo tenant da modificare.'
      ).subscribe();
      return;
    }
    const tenant = this.selection.selected[0];

    const dialogRef = this.dialog.open(TenantDialogComponent, {
      width: '800px',
      data: { tenant: tenant, mode: 'edit' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result && result.success) {
        // Aggiorna la lista dopo la modifica
        this.getAllTenants();
        this.selection.clear();
        this.confirmDialogService.showSuccess('Tenant aggiornato con successo!');
      }
    });
  }

  removeSelectedRows() {
    const selectedTenants = this.selection.selected;
    if (selectedTenants.length === 0) {
      this.confirmDialogService.showInfo(
        'Informazione',
        'Seleziona almeno un tenant da eliminare.'
      ).subscribe();
      return;
    }

    const message = selectedTenants.length === 1
      ? 'Sei sicuro di voler eliminare il tenant selezionato?'
      : `Sei sicuro di voler eliminare ${selectedTenants.length} tenant selezionati?`;

    this.confirmDialogService.showConfirm(
      'Conferma Eliminazione',
      message,
      'Elimina',
      'Annulla'
    ).subscribe((confirmed: boolean) => {
      if (confirmed) {
        selectedTenants.forEach((tenant) => {
          this.tenantServices.deleteTenantById(tenant.tenantId).subscribe({
            next: () => {
              this.tenantList = this.tenantList.filter(t => t.tenantId !== tenant.tenantId);
              this.dataSource.data = [...this.tenantList];
              this.selection.clear();
              this.confirmDialogService.showSuccess('Tenant eliminato con successo!');
            },
            error: (err: any) => {
              console.error(`Errore durante l'eliminazione di ${tenant.tenantId}:`, err);
              this.confirmDialogService.showError(
                'Errore',
                `Errore durante l'eliminazione del tenant ${tenant.tenantId}`
              ).subscribe();
            }
          });
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

  checkboxLabel(row?: any): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row`;
  }
}
