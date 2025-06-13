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
import { ReactiveFormsModule } from '@angular/forms';
import { TenantServices } from '../../../../service/tenant.service';
import { UserService } from '../../../../service/user.service';
import { CommonService } from '../../../../service/common.service';
import { ActivatedRoute, ParamMap } from '@angular/router';

import { error } from 'console';
import { Router } from 'express';

interface Group {
  id: string;
  name: string;
}

interface Tenant {
  id: string;
  name: string;
}

interface ApiResponse<T> {
  body: T;
  isSuccess?: boolean;
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
    ReactiveFormsModule,
  ],
})
export class EditUserDialogComponent implements OnInit {
  userForm: FormGroup;
  tenantList: any = [];
  grpList: any = [];

  selectedTenant: string = '';

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<EditUserDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private userService: UserService,
    private tenantService: TenantServices,
    @Inject(CommonService) private commonServices: CommonService,
    private route: ActivatedRoute
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

  onSubmit() {
    if (this.userForm.invalid) return;

    const formValue = this.userForm.value;
    const userPayload = { ...formValue };
    delete userPayload.confirmPassword;

    if (this.isPersisted) {
      // 🛠 MODIFICA
      this.userService.updateUser(userPayload).subscribe({
        next: (data) => {
          this.manageSuccessOnSave(data);
        },
        error: (err) => {
          this.manageSuccessOnSave(err);
        },
      });
    } else {
      // ✅ CREAZIONE
      this.userService.createUser(userPayload).subscribe({
        next: (data) => {
          this.manageSuccessOnSave(data);
        },
        error: (err) => {
          this.manageSuccessOnSave(err);
        },
      });
    }
  }

  isPersisted = false;
  successMessage: string = '';

  manageSuccessOnSave(data: any) {
    if (data != null && data.body != null) {
      var resultData = data.body;
      this.setFormValues(resultData);
      this.isPersisted = true;
      if (resultData != null && resultData.isSuccess) {
        this.successMessage = 'User creata con successo!';
      }
    }
  }

  setFormValues(resultData: any) {
    console.log('Backend usrGrp:', resultData.usrGrp);
    console.log('Loaded grpList:', this.grpList);
    const selectedGroups = this.grpList.filter((grp: any) =>
      resultData.usrGrp?.some((g: any) => g.grpName === grp.grpName)
    );
    console.log('selectedGroups:', selectedGroups);
    const x = {
      idpId: resultData.idpId,
      userId: resultData.userId,
      firstName: resultData.firstName,
      lastName: resultData.lastName,
      email: resultData.email,
      tenant: resultData.tenant,
      address: resultData.address || '',
      phone: resultData.phone || '',
      gender: resultData.gender || '',
      usrGrp: selectedGroups,
    };

    this.userForm.patchValue(x);
  }

  ngOnInit(): void {
    this.getAllTenants();
    this.laodAllGrp().then(() => {
      this.route.paramMap.subscribe((params: ParamMap) => {
        const userId = params.get('id');
        if (userId) {
          this.userService.getUserById(userId).subscribe({
            next: (data: ApiResponse<UserData>) => {
              this.setFormValues(data.body);
              this.isPersisted = true;
              this.setPasswordValidators(false);
            },
            error: (err: HttpErrorResponse) => {
              console.error(
                'Errore nel caricamento del dettaglio utente:',
                err
              );
            },
          });
        } else {
          this.setPasswordValidators(true);
        }
      });
    });
  }

  async laodAllGrp(): Promise<void> {
    return new Promise((resolve) => {
      this.commonServices.getAllGreoups().subscribe((data: any) => {
        if (data && data.body) {
          this.grpList = data.body;
        }
        resolve();
      });
    });
  }

  async getAllTenants() {
    console.log('get all tenant');
    this.tenantService.getAllTenants().subscribe(
      (data: any) => {
        console.log('Dati ricevuti dal server:', data);
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
      }
    );
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
