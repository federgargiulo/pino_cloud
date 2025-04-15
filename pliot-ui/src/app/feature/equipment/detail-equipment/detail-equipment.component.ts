import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Equipment, EquipmentDetail, EquipmentServices} from '../../../service/equipment.service';
import { ActivatedRoute } from '@angular/router';
import {  SignalServices} from '../../../service/signal.service';
import { Observable, of } from 'rxjs';

const isMockEnabled = true;
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
   isEditingSignal = false; // Differenzia tra aggiunta e modifica
   selectedSignalId: string | null = null; // ID del Signal in modifica

pullerForm!: FormGroup;
showPullerForm = false;
showPullers = false;
isEditingPuller = false;
selectedPullerId: string | null = null;
pullers: any[] = []; // Lista dei pullers

   constructor(
     private route: ActivatedRoute,
     private formBuilder: FormBuilder, // Usato per costruire il form
     private equipmentService: EquipmentServices, // Iniettiamo il servizio
     private signalService: SignalServices
   ) {}


   ngOnInit(): void {


      // Inizializza il form vuoto
      this.equipmentForm = this.formBuilder.group({
        equipmentId: [{ value: '', disabled: true }], // Campo non modificabile
        name: [''],
        tenant: [{ value: '', disabled: true }], // Campo non modificabile
        status: [''],
        updateDttm: [{ value: '', disabled: true }], // Campo non modificabile
        createdDttm: [{ value: '', disabled: true }] // Campo non modificabile
      });

      // Inizializza il form del Signal
      this.initSignalForm();
      // ✅ Inizializza il form del Puller (mancava questa linea)
      this.initPullerForm();
       this.route.paramMap.subscribe(params => {
          this.equipmentId = params.get('id') || ''; // Assicura che non sia null
          console.log("Equipment ID ricevuto:", this.equipmentId);
          // Chiamata al backend per ottenere i dettagli
          this.loadEquipmentDetail(this.equipmentId);
        });
   }


   // Metodo per inizializzare il form dei Signal
   private initSignalForm(): void {
       this.signalForm = this.formBuilder.group({
         signalId: [{ value: '', disabled: false }],
         equipmentId: [''],
         tenant: [{ value: '', disabled: true }],
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

   private initPullerForm(): void {
     this.pullerForm = this.formBuilder.group({
       pullerId: [{ value: '', disabled: true }],
       equipmentId: [{ value: '', disabled: true }],
       tenant: [{ value: '', disabled: true }],
       url: [''],
       apiKey: [''],
       intervalInSec: [''],
       nextExecutions: [''],
       lastStart: [''],
       lastEnd: [''],
       lastExecutionReport: ['']
     });
   }


  // Resetta il form e mostra il form di aggiunta Signal
  toggleSignalForm() {
    this.showSignalForm = true;
    this.isEditingSignal = false;
    this.selectedSignalId = null;
    this.signalForm.reset();
    this.signalForm.patchValue({
      equipmentId: this.equipmentForm.get('equipmentId')?.value,
        tenant: this.equipmentForm.get('tenant')?.value
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

  // **Aggiornare Nome e Stato**
    updateEquipment(): void {
      console.log("updateEquipment:", this.equipmentForm.getRawValue());
      if (!this.equipmentId) {
        console.error("Errore: ID equipment mancante!");
        return;
      }

      this.equipmentService.updateEquipment(this.equipmentId, this.equipmentForm.getRawValue()).subscribe({
        next: () => {
          console.log("Aggiornato con successo!", this.equipmentForm.getRawValue);

        },
        error: (err) => {
          console.error("Errore nell'aggiornamento:", err);
          alert("Errore nell'aggiornamento dell'Equipment.");
        }
      });
    }







      // **Salva o aggiorna un Signal**
      saveSignal() {
        const equipmentId = this.equipmentForm.get('equipmentId')?.value;

        if (this.signalForm.valid) {
          const signalData = this.signalForm.getRawValue();

          if (this.isEditingSignal) {
            // **Modalità Update**
            this.signalService.updateSignal(equipmentId, signalData.signalId, signalData).subscribe(response => {
              console.log('✅ Signal aggiornato:', response);
              this.loadSignals();
              this.resetSignalForm(); // Nasconde il form dopo l'update
            }, error => console.error('❌ Errore aggiornamento Signal:', error));
          } else {
            // **Modalità Add**
            this.signalService.saveSignal(equipmentId, signalData).subscribe(response => {
              console.log('✅ Signal aggiunto:', response);
              this.loadSignals();
              this.resetSignalForm(); // Nasconde il form dopo l'inserimento
            }, error => console.error('❌ Errore aggiunta Signal:', error));
          }
        }
      }




  loadSignals() {

    const equipmentId = this.equipmentForm.get('equipmentId')?.value;


    this.signalService.getSignalsByEquipmentId(equipmentId).subscribe(data => {
      this.signals = data.body;
      console.log("loadSignals. Dati salvati:", this.signals);
    });
  }

  // **Modifica un Signal esistente**
   editSignal(signal: any) {
      this.isEditingSignal = true;
      this.showSignalForm = true;
      this.selectedSignalId = signal.signalId;
      this.signalForm.patchValue(signal);
    }


   // **Resetta il form e torna in modalità aggiunta**
   resetSignalForm() {
       this.signalForm.reset();
       this.showSignalForm = false; // Nasconde il form
       this.isEditingSignal = false;
       this.selectedSignalId = null;
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

    togglePullerForm() {
      this.showPullerForm = true;
      this.isEditingPuller = false;
      this.selectedPullerId = null;
      this.pullerForm.reset();

      const equipmentId = this.equipmentForm.get('equipmentId')?.value;
      const tenant = this.equipmentForm.get('tenant')?.value;

      // Imposta i valori, anche su campi disabilitati
      this.pullerForm.get('tenant')?.enable({ emitEvent: false });
      this.pullerForm.get('equipmentId')?.enable({ emitEvent: false });

      this.pullerForm.patchValue({ equipmentId, tenant });

      this.pullerForm.get('tenant')?.disable({ emitEvent: false });
      this.pullerForm.get('equipmentId')?.disable({ emitEvent: false });
    }


    savePuller() {
      const equipmentId = this.equipmentForm.get('equipmentId')?.getRawValue();

      if (this.pullerForm.valid) {
        const pullerData = this.pullerForm.getRawValue();
        console.log('pullerData:', pullerData);

        if (this.isEditingPuller) {
          // ✅ UPDATE
          this.equipmentService.updatePuller(equipmentId, this.selectedPullerId!, pullerData).subscribe({
            next: (response) => {
              console.log('✅ Puller aggiornato:', response);
              this.loadPullers();
              this.resetPullerForm();
            },
            error: (err) => {
              console.error('❌ Errore aggiornamento Puller:', err);
            }
          });
        } else {
          // ✅ CREATE
          this.equipmentService.createEquipmentPuller(equipmentId, pullerData).subscribe({
            next: (response) => {
              console.log('✅ Puller aggiunto:', response);
              this.loadPullers();
              this.resetPullerForm();
            },
            error: (err) => {
              console.error('❌ Errore aggiunta Puller:', err);
            }
          });
        }
      }
    }



resetPullerForm() {
  this.pullerForm.reset();                     // Reset completo dei campi
  this.showPullerForm = false;                 // Nasconde il form
  this.isEditingPuller = false;                // Torna in modalità "aggiunta"
  this.selectedPullerId = null;                // Nessun puller selezionato

  // Disabilita nuovamente i campi equipmentId e tenant
  this.pullerForm.get('equipmentId')?.disable({ emitEvent: false });
  this.pullerForm.get('tenant')?.disable({ emitEvent: false });
}


    loadPullers() {
      const equipmentId = this.equipmentForm.get('equipmentId')?.value;
      const tenant = this.equipmentForm.get('tenant')?.value;
      this.equipmentService.getPullersByEquipmentId(equipmentId).subscribe(data => {
        this.pullers = data.body;
        console.log("loadPullers. Dati salvati:", this.pullers);
        this.showPullerForm = false; // Nasconde eventualmente il form
        this.showPullers = true;     // Mostra la lista (se usi *ngIf)
      });
    }




editPuller(puller: any) {
  this.isEditingPuller = true;
  this.showPullerForm = true;
  this.selectedPullerId = puller.pullerId;

  this.pullerForm.get('tenant')?.enable({ emitEvent: false });
  this.pullerForm.get('equipmentId')?.enable({ emitEvent: false });

  this.pullerForm.patchValue(puller);

  this.pullerForm.get('tenant')?.disable({ emitEvent: false });
  this.pullerForm.get('equipmentId')?.disable({ emitEvent: false });
}

deletePullerById(pullerId: string, index: number) {
  if (!confirm("Sei sicuro di voler eliminare questo Puller?")) {
    return;
  }

  const equipmentId = this.equipmentForm.get('equipmentId')?.value;

  this.equipmentService.deletePullerById(equipmentId, pullerId).subscribe({
    next: () => {
      console.log(`✅ Puller con ID ${pullerId} eliminato.`);
      this.pullers.splice(index, 1);
    },
    error: (err) => {
      console.error("❌ Errore durante l'eliminazione del puller:", err);
    }
  });
}

togglePullerList() {
  this.showPullers = !this.showPullers;
}

 }

