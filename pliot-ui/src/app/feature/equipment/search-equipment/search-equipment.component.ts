import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EquipmentServices } from '../../../service/equipment.service';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import { EquipmentConfirmDialogComponent } from './equipment-confirm-dialog/equipment-confirm-dialog.component';
import { DeleteConfirmDialogComponent } from './equipment-confirm-dialog/delete-confirm-dialog.component';
import { EquipmentDialogComponent } from './equipment-dialog/equipment-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-search-equipment',
  standalone: false,
  templateUrl: './search-equipment.component.html',
  styleUrl: './search-equipment.component.scss'
})
export class SearchEquipmentComponent implements OnInit {

  displayedColumns: string[] = ['select', 'equipmentId', 'name', 'tenant', 'status', 'createdDttm', 'updateDttm'];
  dataSource = new MatTableDataSource<any>([]);
  selection = new SelectionModel<any>(true, []);
  searchValue: string = '';

  constructor(
    private equipmentServices: EquipmentServices,
    private router: Router,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    console.log("init");
    this.getAllEquipment();
  }

  openAddEquipmentDialog(): void {
    const dialogRef = this.dialog.open(EquipmentDialogComponent, {
      width: '600px',
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.getAllEquipment(); // ricarica la tabella
      }
    });
  }

  async getAllEquipment() {
    console.log("get all equipment");
    this.equipmentServices.getAllEquipment().subscribe((data: any) => {
      console.log("Dati ricevuti dal server:", data);
      if (data != null && data.body != null) {
        this.dataSource.data = data.body;
      }
    },
    (error: any) => {
      if (error) {
        if (error.status === 404 && error.error?.message) {
          this.dataSource.data = [];
          this.selection.clear();
        }
      }
    });
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

  refreshEquipment() {
    this.getAllEquipment();
  }

  editSelectedRows(): void {
    const selected = this.selection.selected[0];
    if (selected) {
      const dialogRef = this.dialog.open(EquipmentConfirmDialogComponent, {
        panelClass: 'equipment-edit-dialog',
        data: { equipmentId: selected.equipmentId }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result === true) {
          this.getAllEquipment(); // ricarica la tabella
          this.selection.clear();
        }
      });
    }
  }

  editSelectedEquipment() {
    const selected = this.selection.selected[0];
    if (selected) {
      this.router.navigate(['/detail-equipment', selected.equipmentId]);
    }
  }

  deleteSelectedEquipment() {
    if (this.selection.isEmpty()) {
      this.snackBar.open('Seleziona almeno un equipment da eliminare', 'Chiudi', {
        duration: 3000,
        horizontalPosition: 'end',
        verticalPosition: 'top'
      });
      return;
    }

    const dialogRef = this.dialog.open(DeleteConfirmDialogComponent, {
      width: '400px',
      data: {
        message: `Sei sicuro di voler eliminare ${this.selection.selected.length} equipment selezionato/i?`
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const currentData = [...this.dataSource.data];
        let successCount = 0;
        let errorCount = 0;

        this.selection.selected.forEach((equipment: any) => {
          this.equipmentServices.deleteEquipmentById(equipment.equipmentId).subscribe({
            next: () => {
              const index = currentData.findIndex(e => e.equipmentId === equipment.equipmentId);
              if (index > -1) {
                currentData.splice(index, 1);
                successCount++;
              }

              if (successCount + errorCount === this.selection.selected.length) {
                this.dataSource.data = currentData;
                this.selection.clear();

                if (successCount > 0) {
                  this.snackBar.open(`${successCount} equipment eliminato/i con successo`, 'Chiudi', {
                    duration: 3000,
                    horizontalPosition: 'end',
                    verticalPosition: 'top'
                  });
                }
                if (errorCount > 0) {
                  this.snackBar.open(`Errore durante l'eliminazione di ${errorCount} equipment`, 'Chiudi', {
                    duration: 3000,
                    horizontalPosition: 'end',
                    verticalPosition: 'top'
                  });
                }
              }
            },
            error: (error: HttpErrorResponse) => {
              console.error(`Errore durante l'eliminazione dell'equipment ${equipment.equipmentId}:`, error);
              errorCount++;

              if (successCount + errorCount === this.selection.selected.length) {
                this.dataSource.data = currentData;
                this.selection.clear();

                if (successCount > 0) {
                  this.snackBar.open(`${successCount} equipment eliminato/i con successo`, 'Chiudi', {
                    duration: 3000,
                    horizontalPosition: 'end',
                    verticalPosition: 'top'
                  });
                }
                if (errorCount > 0) {
                  this.snackBar.open(`Errore durante l'eliminazione di ${errorCount} equipment`, 'Chiudi', {
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
