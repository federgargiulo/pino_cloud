import { Component } from '@angular/core';
import { FormBuilder, FormGroup,  FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TenantServices, Tenant } from '../../../service/tenant.service';


@Component({
  selector: 'app-add-tenant',
  standalone: false,
  templateUrl: './add-tenant.component.html',
  styleUrl: './add-tenant.component.scss'
})
export class AddTenantComponent {
 tenantForm: FormGroup;


 constructor(private fb: FormBuilder, private tenantService: TenantServices, private router: Router) {
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
       createdDttm: [new Date().toISOString()],  // Campo hidden
       updateDttm: [new Date().toISOString()]   // Campo hidden
     });
   }

   onSubmit() {
     if (this.tenantForm.valid) {
       this.tenantService.addTenant(this.tenantForm.value).subscribe(() => {
         alert('Tenant created successfully');
         this.router.navigate(['/search-tenant']); // Naviga alla lista dopo il salvataggio
       });
     }
   }

 }
