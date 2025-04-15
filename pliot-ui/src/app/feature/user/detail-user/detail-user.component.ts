import { Component } from '@angular/core';
import { TenantServices } from '../../../service/tenant.service';
import { UserService } from '../../../service/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from 'express';
import { ActivatedRoute } from '@angular/router';
import { error } from 'console';
import { CommonService } from '../../../service/common.service';
const isMockEnabled = true;


@Component({
  selector: 'app-detail-user',
  standalone: false,
  templateUrl: './detail-user.component.html',
  styleUrl: './detail-user.component.css'
})
export class DetailUserComponent {

    userForm: FormGroup;
    tenantList: any = [];
    grpList: any = [];

    selectedTenant: string = '';

    constructor( private route: ActivatedRoute,
                 private fb: FormBuilder, private tenantServices: TenantServices ,
                 private userService : UserService ,
                 private commonServices: CommonService ) {

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
         usrGrp:  [[]]
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
          }
        });
      } else {
        // ✅ CREAZIONE
        this.userService.createUser(userPayload).subscribe({
          next: (data) => {
            this.manageSuccessOnSave(data);

          },
          error: (err) => {
            this.manageSuccessOnSave(err);
          }
        });
      }
    }


    isPersisted = false;
    successMessage: string = '';

    manageSuccessOnSave( data:any ){
      if (data != null && data.body != null) {
        var resultData = data.body;
        this.setFormValues( resultData );
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
        usrGrp: selectedGroups
      };

      this.userForm.patchValue(x);
    }



    ngOnInit(): void {
      this.getAllTenants();
      this.laodAllGrp().then(() => {
        this.route.paramMap.subscribe(params => {
          const userId = params.get('id');

          if (userId) {
            if (isMockEnabled) {
              const mockUser = {
                idpId: 'idp-mock',
                userId: 'user123',
                firstName: 'Mario',
                lastName: 'Rossi',
                email: 'mario@example.com',
                tenant: 'tenant1',
                address: 'Via Roma 1',
                phone: '333444555',
                gender: 'M',
                usrGrp: [{ grpName: 'Admin' }]
              };
              this.setFormValues(mockUser);
              this.isPersisted = true;
              this.setPasswordValidators(false);
            } else {
              this.userService.getUserById(userId).subscribe({
                next: (data) => {
                  this.setFormValues(data.body);
                  this.isPersisted = true;
                  this.setPasswordValidators(false);
                },
                error: (err) => {
                  console.error('Errore nel caricamento del dettaglio utente:', err);
                }
              });
            }
          } else {
            this.setPasswordValidators(true);
          }
        });
      });
    }



    async laodAllGrp(): Promise<void> {
      return new Promise((resolve) => {
        if (isMockEnabled) {
          this.grpList = [
            { grpName: 'Admin' },
            { grpName: 'User' },
            { grpName: 'Manager' }
          ];
          resolve();
        } else {
          this.commonServices.getAllGreoups().subscribe((data: any) => {
            if (data?.body) {
              this.grpList = data.body;
            }
            resolve();
          });
        }
      });
    }


async getAllTenants() {
  console.log("get all tenant");
  if (isMockEnabled) {
    this.tenantList = [
      { tenantId: 'tenant1', name: 'Tenant Uno' },
      { tenantId: 'tenant2', name: 'Tenant Due' }
    ];
    return;
  }

  console.log( "get all tenant" )
  this.tenantServices.getAllTenants().subscribe((data : any) => {
   console.log("Dati ricevuti dal server:", data)
    if (data != null && data.body != null) {
      var resultData = data.body;

      if (resultData) {
        this.tenantList = resultData;
      }
    }
  },
  (error : any)=> {
      if (error) {
        if (error.status == 404) {
          if(error.error && error.error.message){
            this.tenantList = [];
          }
        }
      }
    });
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
}
