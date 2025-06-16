import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSelectModule } from '@angular/material/select';
import { ReactiveFormsModule } from '@angular/forms';
import { TenantServices } from '../../../../service/tenant.service';
import { UserService } from '../../../../service/user.service';
import { CommonService } from '../../../../service/common.service';

interface Group {
  id: string;
  name: string;
}

interface Tenant {
  id: string;
  name: string;
}

interface UserData {
  idpId: string;
  userId: string;
  firstName: string;
  lastName: string;
  email: string;
  tenant: string;
  address?: string;
  phone?: string;
  gender?: string;
  usrGrp?: any[];
}

@Component({
  selector: 'app-edit-user-dialog',
  templateUrl: './edit-user-dialog.component.html',
  styleUrls: ['./edit-user-dialog.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatToolbarModule,
    MatSelectModule,
    ReactiveFormsModule,
  ],
})
export class EditUserDialogComponent implements OnInit {
  userForm: FormGroup;
  tenantList: any[] = [];
  grpList: any[] = [];

  isPersisted = false;
  successMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<EditUserDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { user?: UserData },
    private userService: UserService,
    private tenantService: TenantServices,
    private commonServices: CommonService
  ) {
    this.userForm = this.fb.group({
      idpId: [''],
      userId: ['', [Validators.required]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      tenant: ['', [Validators.required]],
      password: [''],
      address: [''],
      phone: [''],
      gender: [''],
      usrGrp: [[]],
    });
  }

  ngOnInit(): void {
    this.getAllTenants();
    this.loadAllGroups().then(() => {
      if (this.data?.user) {
        this.setFormValues(this.data.user);
        this.isPersisted = true;
        this.setPasswordValidators(false);
      } else {
        this.setPasswordValidators(true);
      }
    });
  }

  async loadAllGroups(): Promise<void> {
    return new Promise((resolve) => {
      this.commonServices.getAllGreoups().subscribe((data: any) => {
        if (data && data.body) {
          this.grpList = data.body;
        }
        resolve();
      });
    });
  }

  getAllTenants() {
    this.tenantService.getAllTenants().subscribe(
      (data: any) => {
        if (data && data.body) {
          this.tenantList = data.body;
        }
      },
      (error: any) => {
        if (error?.status === 404) {
          this.tenantList = [];
        }
      }
    );
  }

  setFormValues(user: UserData) {
    const selectedGroups = this.grpList.filter((grp: any) =>
      user.usrGrp?.some((g: any) => g.grpName === grp.grpName)
    );

    const patch = {
      idpId: user.idpId,
      userId: user.userId,
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email,
      tenant: user.tenant,
      address: user.address || '',
      phone: user.phone || '',
      gender: user.gender || '',
      usrGrp: selectedGroups,
    };

    this.userForm.patchValue(patch);
  }

  setPasswordValidators(isRequired: boolean) {
    const passwordControl = this.userForm.get('password');
    if (isRequired) {
      passwordControl?.setValidators([Validators.required]);
    } else {
      passwordControl?.clearValidators();
    }
    passwordControl?.updateValueAndValidity();
  }

  compareGroups = (o1: any, o2: any): boolean => {
    return o1 && o2 && o1.grpName === o2.grpName;
  };

  onSubmit() {
    if (this.userForm.invalid) return;

    const formValue = this.userForm.value;
    const userPayload = { ...formValue };

    if (this.isPersisted) {
      this.userService.updateUser(userPayload).subscribe({
        next: (data) => this.manageSuccessOnSave(data),
        error: (err) => this.manageSuccessOnSave(err),
      });
    } else {
      this.userService.createUser(userPayload).subscribe({
        next: (data) => this.manageSuccessOnSave(data),
        error: (err) => this.manageSuccessOnSave(err),
      });
    }
  }

  manageSuccessOnSave(data: any) {
    if (data?.body) {
      this.setFormValues(data.body);
      this.isPersisted = true;
      this.successMessage = this.isPersisted
        ? 'Utente aggiornato con successo!'
        : 'Utente creato con successo!';
      this.dialogRef.close(data.body);
    }
  }

  onCancel() {
    this.dialogRef.close();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onYesClick(): void {
    this.onSubmit();
  }
}
