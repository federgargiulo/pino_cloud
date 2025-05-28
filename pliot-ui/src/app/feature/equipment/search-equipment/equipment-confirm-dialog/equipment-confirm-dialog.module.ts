import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { EquipmentConfirmDialogComponent } from './equipment-confirm-dialog.component';
import { MatTableModule } from '@angular/material/table';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormField } from '@angular/material/form-field';

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
    MatFormField
  ],
  exports: [
    EquipmentConfirmDialogComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EquipmentConfirmDialogModule { }
