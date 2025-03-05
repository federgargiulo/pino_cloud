import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Equipment, EquipmentDetail, EquipmentServices} from '../../../service/equipment.service';
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
   equipment!: Equipment;
   equipmentDetail: any; // Dati dell'attrezzatura ricevuti dal backend
   loading = false;
   signalForm!: FormGroup; // Aggiunto '!' per indicare che sarà inizializzato nel costruttore
   showSignalForm = false;
   signals: any[] = [];

  constructor(
     private route: ActivatedRoute,
      private formBuilder: FormBuilder, // Usato per costruire il form
     private equipmentService: EquipmentServices // Iniettiamo il servizio
      //private signalService: SignalService
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
           tenant: [{ value: '', disabled: true }], // Campo non modificabile
           status: [''],
           updateDttm: [{ value: '', disabled: true }], // Campo non modificabile
           createdDttm: [{ value: '', disabled: true }] // Campo non modificabile
         });

         this.signalForm = this.formBuilder.group({
               id: [''],
               equipmentId: [''],
               unitOfMeasurement: [''],
               name: [''],
               minVal: [''],
               maxVal: [''],
               downRedLimit: [''],
               downYellowLimit: [''],
               upRedLimit: [''],
               upYellowLimit: [''],
               createdDttm: [''],
               updateDttm: ['']
             });
   }

 toggleSignalForm() {
    console.info( " toggleSignalForm ")
    this.showSignalForm = !this.showSignalForm;
    console.info( " this.showSignalForm:"+this.showSignalForm)
    if (this.showSignalForm) {
      this.signalForm.patchValue({
        equipmentId: this.equipmentForm.get('equipmentId')?.value
      });
    }
  }



   saveSignal() {
      const newSignal = this.signalForm.value;
      console.info( " newSignal id " + newSignal.signalId )
      //this.signalService.saveSignal(newSignal).subscribe(response => {
        //this.showSignalForm = false;
       // this.loadSignals();
      //});
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

  // **Aggiornare Nome e Stato**
    updateEquipment(): void {
      if (!this.equipmentId) {
        console.error("Errore: ID equipment mancante!");
        return;
      }

      this.equipmentService.updateEquipment(this.equipmentId, this.equipmentForm.value).subscribe({
        next: () => {
          console.log("Aggiornato con successo!", this.equipmentForm.value);

        },
        error: (err) => {
          console.error("Errore nell'aggiornamento:", err);
          alert("Errore nell'aggiornamento dell'Equipment.");
        }
      });
    }


  }

