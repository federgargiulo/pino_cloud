import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';
import { TenantDetail, TenantServices} from '../../../service/tenant.service';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-detail-tenant',
  standalone: false,
  templateUrl: './detail-tenant.component.html',
  styleUrl: './detail-tenant.component.css'
})
export class DetailTenantComponent implements OnInit {
  tenantId!: string; // ID della risorsa
  tenantForm!: FormGroup; // Form reattivo
  tenantDetail: any; // Dati dell'attrezzatura ricevuti dal backend
  loading = false;

  constructor(
     private router: ActivatedRoute,
     private formBuilder: FormBuilder, // Usato per costruire il form
     private tenantService: TenantServices // Iniettiamo il servizio


   ) { }


 ngOnInit(): void {
     this.router.paramMap.subscribe(params => {
       this.tenantId = params.get('id') || ''; // Assicura che non sia null
       console.log("Tenant ID ricevuto:", this.tenantId);

       // Chiamata al backend per ottenere i dettagli
       this.loadTenantDetail(this.tenantId);
     });
        this.tenantForm = this.formBuilder.group({
          tenantId: [{ value: '', disabled: true }], // Campo non modificabile
          name:  [''],
          description:  [''],
          updateDttm: [{ value: '', disabled: true }], // Campo non modificabile
          createdDttm: [{ value: '', disabled: true }], // Campo non modificabile
          email: ['', [Validators.required, Validators.email]],
          profile: [''],
          country: [''],
          state: [''],
          zipCode: [''],
          address: ['']
        });

   }


   // Metodo per caricare i dettagli dell'attrezzatura
     loadTenantDetail(id: string): void {
       this.tenantService.getTenantById(id).subscribe({
         next: (response) => {
           this.tenantDetail = response.body || response; // Salva il body della risposta // Salva i dati per visualizzarli nella UI
            console.log("Dati ricevuti dal server:", this.tenantDetail);

           // Popola il form con i dati ricevuti
           this.tenantForm.patchValue(this.tenantDetail);
         },
         error: (err) => {
           console.error("Errore nel caricamento dei dettagli:", err);
         }
       });
     }

  // **Aggiornare Nome e Stato**
    updateTenant(): void {
    console.error("tenantId:", this.tenantId);
      if (!this.tenantId) {
        console.error("Errore: ID tenant mancante!");
        return;
      }

      this.tenantService.updateTenant(this.tenantId, this.tenantForm.value).subscribe({
        next: () => {
          console.log("Aggiornato con successo!", this.tenantForm.value);

        },
        error: (err) => {
          console.error("Errore nell'aggiornamento:", err);
          alert("Errore nell'aggiornamento del Tenant.");
        }
      });
    }
  }

