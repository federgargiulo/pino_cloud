import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EquipmentServices } from '../../../../service/equipment.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-equipment-dialog',
  templateUrl: './equipment-dialog.component.html',
  standalone: false
})
export class EquipmentDialogComponent implements OnInit {
  equipmentForm: FormGroup;
  isEditMode: boolean = false;
  addEquipmentForm: EquipmentForm = new EquipmentForm();
  @ViewChild("equipmentForm")
  EquipmentForm!: NgForm;
  isSubmitted: boolean = false;

  constructor(
    private router: Router,
    private httpProvider: EquipmentServices,
    private fb: FormBuilder,
    private equipmentService: EquipmentServices,
    private dialogRef: MatDialogRef<EquipmentDialogComponent>,
    private snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.equipmentForm = this.fb.group({
      equipmentId: [''],
      name: ['', Validators.required],
      tenant: [''],
      version: ['']
    });
  }

  ngOnInit(): void {
    if (this.data && this.data.equipmentId) {
      this.isEditMode = true;
      this.loadEquipmentData();
    }
  }

  addEquipment(isValid: any) {
    this.isSubmitted = true;
    if (isValid) {
      console.info( " addEquipmentForm " + this.addEquipmentForm.name )
      this.httpProvider.saveEquipment({name:  this.addEquipmentForm.name }).subscribe(async data => {
        if (data != null && data.body != null) {
          if (data != null && data.body != null) {
            var resultData = data.body;
            if (resultData != null && resultData.isSuccess) {

              setTimeout(() => {
                this.router.navigate(['/']);
              }, 500);
            }
          }
        }
      },
      async error => {

        setTimeout(() => {
          this.router.navigate(['/']);
        }, 500);
      });
    }
  }

  loadEquipmentData(): void {
    this.equipmentService.getEquipmentById(this.data.equipmentId).subscribe({
      next: (response: any) => {
        if (response && response.body) {
          const equipment = response.body;
          this.equipmentForm.patchValue({
            equipmentId: equipment.equipmentId,
            name: equipment.name,
            tenant: equipment.tenant,
            version: equipment.version
          });
        }
      },
      error: (error: any) => {
        console.error('Errore nel caricamento equipment:', error);
        this.snackBar.open('Errore nel caricamento dei dati equipment', 'Chiudi', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top'
        });
      }
    });
  }

  onSubmit(): void {
    if (this.equipmentForm.valid) {
      const equipmentData = this.equipmentForm.value;

      if (this.isEditMode) {
        const equipmentId = equipmentData.equipmentId;
        // Rimuovo equipmentId dal model per l'update
        const { equipmentId: _, ...updateData } = equipmentData;

        this.equipmentService.updateEquipment(equipmentId, updateData).subscribe({
          next: (response: any) => {
            this.snackBar.open('Equipment aggiornato con successo', 'Chiudi', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top'
            });
            this.dialogRef.close(true);
          },
          error: (error: any) => {
            console.error('Errore nell\'aggiornamento equipment:', error);
            this.snackBar.open('Errore nell\'aggiornamento equipment', 'Chiudi', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top'
            });
          }
        });
      } else {
        this.equipmentService.saveEquipment(equipmentData).subscribe({
          next: (response: any) => {
            this.snackBar.open('Equipment creato con successo', 'Chiudi', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top'
            });
            this.dialogRef.close(true);
          },
          error: (error: any) => {
            console.error('Errore nella creazione equipment:', error);
            this.snackBar.open('Errore nella creazione equipment', 'Chiudi', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top'
            });
          }
        });
      }
    }
  }
}

export class EquipmentForm {
  equipmentId: string="";
  tenant: string="";
  name: string="";
  version: string="";
invalid: unknown;
}
