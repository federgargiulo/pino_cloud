import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EquipmentServices } from '../../../service/equipment.service';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';

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

  constructor(private equipmentServices: EquipmentServices, private router: Router) {}

  ngOnInit(): void {
    console.log("init");
    this.getAllEquipment();
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
        }
      }
    });
  }

  refreshEquipment() {
    this.getAllEquipment();
  }

  editSelectedEquipment() {
    const selected = this.selection.selected[0];
    if (selected) {
      this.router.navigate(['/detail-equipment', selected.equipmentId]);
    }
  }

  deleteSelectedEquipment() {
    const selectedEquipments = this.selection.selected;
    selectedEquipments.forEach(equipment => {
      this.deleteEquipment(equipment.equipmentId);
    });
  }

  deleteEquipment(id: string) {
    if (!confirm(`Sei sicuro di voler eliminare l'equipment con ID ${id}?`)) {
      return;
    }

    this.equipmentServices.deleteEquipmentById(id).subscribe({
      next: () => {
        console.log(`Equipment con ID ${id} eliminato`);
        this.dataSource.data = this.dataSource.data.filter(e => e.equipmentId !== id);
        this.selection.clear();
      },
      error: (err) => {
        console.error("Errore durante l'eliminazione:", err);
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
