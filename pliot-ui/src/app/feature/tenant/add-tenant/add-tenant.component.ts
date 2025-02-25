import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TenantServices, Tenant } from '../../../service/tenant.service';


@Component({
  selector: 'app-add-tenant',
  standalone: false,
  templateUrl: './add-tenant.component.html',
  styleUrl: './add-tenant.component.css'
})
export class AddTenantComponent {
 tenantForm: FormGroup;

 constructor(
     private fb: FormBuilder,
     private tenantServices: TenantServices,
     private router: Router
   ) {
     this.tenantForm = this.fb.group({
       tenantId: [''],
       name:  [''],
       description:  [''],
       createdDttm: [new Date().toISOString()],
       updateDttm: [new Date().toISOString()],
       email: ['', [Validators.required, Validators.email]],
       profile: [''],
       country: [''],
       state: [''],
       zipCode: [''],
       address: ['']
     });
   }

  onSubmit(): void {

  const newTenant: Tenant = this.tenantForm.value;
   console.info( " newTenant id " + newTenant.tenantId )
   this.tenantServices.saveTenant(newTenant).subscribe(() => {
      alert('Tenant creato con successo!');
      this.router.navigate(['/']); // 🔥 Torna alla home o alla lista dei Tenant
   });
  }
 }

