import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { EquipmentDetail, EquipmentServices} from '../../../service/equipment.service';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-detail-equipment',
  standalone: false,
  templateUrl: './detail-equipment.component.html',
  styleUrl: './detail-equipment.component.css'
})
export class DetailEquipmentComponent implements OnInit {
  equipmentId!: string; // ID della risorsa
   equipmentForm!: FormGroup; // Form reattivo
  equipmentDetail: any; // Dati dell'attrezzatura ricevuti dal backend
   loading = false;

  constructor(
     private route: ActivatedRoute,
      private formBuilder: FormBuilder, // Usato per costruire il form
     private equipmentService: EquipmentServices // Iniettiamo il servizio
   ) {}


 ngOnInit(): void {
     this.route.paramMap.subscribe(params => {
       this.equipmentId = params.get('id') || ''; // Assicura che non sia null
       console.log("Equipment ID ricevuto:", this.equipmentId);

       // Chiamata al backend per ottenere i dettagli
       this.loadEquipmentDetail(this.equipmentId);
     });


     // Inizializza il form vuoto
         this.equipmentForm = this.formBuilder.group({
           equipmentId: [{ value: '', disabled: true }], // Campo non modificabile
           name: [''],
           tenant: [''],
           status: [''],
           updateDttm: [{ value: '', disabled: true }], // Campo non modificabile
           createdDttm: [{ value: '', disabled: true }] // Campo non modificabile
         });
   }


   // Metodo per caricare i dettagli dell'attrezzatura
     loadEquipmentDetail(id: string): void {
       this.equipmentService.getEquipmentById(id).subscribe({
         next: (response) => {
           this.equipmentDetail = response.body || response; // Salva il body della risposta // Salva i dati per visualizzarli nella UI
            console.log("Dati ricevuti dal server:", this.equipmentDetail);

           // Popola il form con i dati ricevuti
           this.equipmentForm.patchValue(this.equipmentDetail);
         },
         error: (err) => {
           console.error("Errore nel caricamento dei dettagli:", err);
         }
       });
     }

 // Metodo per aggiornare i dati
  updateEquipment(): void {
    if (this.equipmentForm.valid) {
      const updatedData = this.equipmentForm.getRawValue(); // Ottieni i valori del form
      console.log("Dati aggiornati:", updatedData);
      // Puoi ora inviare questi dati al backend
    }
  }
}
