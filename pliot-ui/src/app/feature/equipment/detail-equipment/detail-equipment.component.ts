import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Equipment, EquipmentDetail, EquipmentServices} from '../../../service/equipment.service';
import { ActivatedRoute } from '@angular/router';
import {  SignalServices} from '../../../service/signal.service';

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
   isEditingSignal = false;
   selectedSignalId: string | null = null; // ID del Signal in modifica

   constructor(
     private route: ActivatedRoute,
     private formBuilder: FormBuilder, // Usato per costruire il form
     private equipmentService: EquipmentServices, // Iniettiamo il servizio
     private signalService: SignalServices
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
               signalId: [''],
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




  addSignal() {
      const equipmentId = this.equipmentForm.get('equipmentId')?.value;
      const signalId = this.signalForm.get('signalId')?.value;
      console.log('Saving Signal for signalId:', signalId);

      if (this.signalForm.valid) {
        const newSignal = this.signalForm.value;
        this.signalService.saveSignal(equipmentId, newSignal).subscribe(response => {
          console.log('Signal saved successfully:', response);
          this.showSignalForm = false;
          this.loadSignals(); // Ricarica la lista dei segnali
          this.signalForm.reset();
        }, error => {
          console.error('Error saving signal:', error);
        });
      } else {
        console.log("Invalid form submission.");
      }
    }

  loadSignals() {

    const equipmentId = this.equipmentForm.get('equipmentId')?.value;
    this.signalService.getSignalsByEquipmentId(equipmentId).subscribe(data => {
      this.signals = data.body;
      console.log("loadSignals. Dati salvati:", this.signals);
    });
  }


   // **Eliminare un Signal**
   deleteSignal(equipmentId: string, idSignal: string, i:number): void {
      if (!confirm("Sei sicuro di voler eliminare questo Signal?")) {
        return;
      }

      this.signalService.deleteSignalById(equipmentId, idSignal).subscribe({
        next: () => {
          console.log(`Signal con ID ${idSignal} eliminato con successo da equipment con id  ${equipmentId} !`);

         this.signals.splice(i,1);
          console.log(`this.signals`, this.signals);
        },
        error: (err) => {
          console.error("Errore durante l'eliminazione:", err);
        }
      });
    }



 }

