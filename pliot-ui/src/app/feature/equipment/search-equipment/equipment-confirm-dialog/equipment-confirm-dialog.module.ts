import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { EquipmentConfirmDialogComponent } from './equipment-confirm-dialog.component';
import { MatTableModule } from '@angular/material/table';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatToolbarModule } from '@angular/material/toolbar';
import { DeleteConfirmDialogComponent } from './delete-confirm-dialog.component';
@NgModule({
  declarations: [
    EquipmentConfirmDialogComponent
  ],
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    MatTableModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatExpansionModule,
    MatIconModule,
    MatDividerModule,
    MatCheckboxModule,
    MatToolbarModule,
    DeleteConfirmDialogComponent
  ],
  exports: [
    EquipmentConfirmDialogComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EquipmentConfirmDialogModule { }
