import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { TenantServices, Tenant } from '../../../../service/tenant.service';
import { ConfirmDialogService } from '../confirm-dialog/confirm-dialog.service';

@Component({
  selector: 'app-tenant-dialog',
  templateUrl: './tenant-dialog.component.html',
  styleUrls: ['./tenant-dialog.component.scss'],
  standalone: false
})
export class TenantDialogComponent implements OnInit {
  tenantForm: FormGroup;
  isEditMode: boolean = false;
  dialogTitle: string = 'Create Tenant';

  constructor(
    private fb: FormBuilder,
    private tenantService: TenantServices,
    private dialogRef: MatDialogRef<TenantDialogComponent>,
    private confirmDialogService: ConfirmDialogService,
    @Inject(MAT_DIALOG_DATA) public data: { tenant?: Tenant, mode: 'create' | 'edit' }
  ) {
    this.isEditMode = data.mode === 'edit';
    this.dialogTitle = this.isEditMode ? 'Edit Tenant' : 'Create Tenant';

    this.tenantForm = this.fb.group({
      tenantId: [''],
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      description: [''],
      profile: [''],
      country: [''],
      state: [''],
      zipCode: [''],
      address: [''],
      createdDttm: [new Date().toISOString()],
      updateDttm: [new Date().toISOString()]
    });
  }

  ngOnInit(): void {
    if (this.isEditMode && this.data.tenant) {
      this.tenantForm.patchValue(this.data.tenant);
    }
  }

  onSubmit() {
    if (this.tenantForm.valid) {
      const tenantData = this.tenantForm.value;

      if (this.isEditMode) {
        // Aggiorna il timestamp di modifica
        tenantData.updateDttm = new Date().toISOString();

        this.tenantService.updateTenant(tenantData.tenantId, tenantData).subscribe({
          next: () => {
            this.dialogRef.close({ success: true, action: 'updated', data: tenantData });
          },
          error: (error: any) => {
            console.error('Errore durante l\'aggiornamento del tenant:', error);
            this.confirmDialogService.showError(
              'Errore',
              'Errore durante l\'aggiornamento del tenant'
            ).subscribe();
          }
        });
      } else {
        this.tenantService.addTenant(tenantData).subscribe({
          next: () => {
            this.dialogRef.close({ success: true, action: 'created', data: tenantData });
          },
          error: (error: any) => {
            console.error('Errore durante la creazione del tenant:', error);
            this.confirmDialogService.showError(
              'Errore',
              'Errore durante la creazione del tenant'
            ).subscribe();
          }
        });
      }
    }
  }

  onCancel() {
    this.dialogRef.close();
  }
}
