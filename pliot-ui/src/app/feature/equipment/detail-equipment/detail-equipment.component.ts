import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { EquipmentServices } from '../../../service/equipment.service';
import { SignalServices } from '../../../service/signal.service';

@Component({
  selector: 'app-detail-equipment',
  standalone: false,
  templateUrl: './detail-equipment.component.html',
  styleUrls: ['./detail-equipment.component.scss']
})
export class DetailEquipmentComponent implements OnInit {

  equipmentId!: string;
  equipmentForm!: FormGroup;
  equipmentDetail: any;
  loading = false;

  signalForm!: FormGroup;
  signals = new MatTableDataSource<any>([]);
  showSignalForm = false;
  isEditingSignal = false;
  selectedSignalId: string | null = null;

  pullerForm!: FormGroup;
  pullers = new MatTableDataSource<any>([]);
  showPullerForm = false;
  showPullers = false;
  isEditingPuller = false;
  selectedPullerId: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private equipmentService: EquipmentServices,
    private signalService: SignalServices
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.equipmentId = params.get('id') || '';
      this.loadEquipmentDetail(this.equipmentId);
    });

    this.equipmentForm = this.formBuilder.group({
      equipmentId: [{ value: '', disabled: true }],
      name: [''],
      tenant: [{ value: '', disabled: true }],
      status: [''],
      createdDttm: [{ value: '', disabled: true }],
      updateDttm: [{ value: '', disabled: true }]
    });

    this.initSignalForm();
    this.initPullerForm();
  }

  private initSignalForm(): void {
    this.signalForm = this.formBuilder.group({
      signalId: [''],
      equipmentId: [''],
      tenant: [''],
      unitOfMeasurement: [''],
      name: [''],
      minVal: [''],
      maxVal: [''],
      downRedLimit: [''],
      downYellowLimit: [''],
      upRedLimit: [''],
      upYellowLimit: ['']
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

  loadEquipmentDetail(id: string): void {
    this.equipmentService.getEquipmentById(id).subscribe({
      next: (response) => {
        this.equipmentDetail = response.body || response;
        this.equipmentForm.patchValue(this.equipmentDetail);
      },
      error: (err) => {
        console.error("Errore nel caricamento dei dettagli:", err);
      }
    });
  }

  updateEquipment(): void {
    if (!this.equipmentId) {
      console.error("Errore: ID equipment mancante!");
      return;
    }

    this.equipmentService.updateEquipment(this.equipmentId, this.equipmentForm.getRawValue()).subscribe({
      next: () => {
        console.log("Equipment aggiornato con successo!");
      },
      error: (err) => {
        console.error("Errore nell'aggiornamento:", err);
      }
    });
  }

  toggleSignalForm(): void {
    this.showSignalForm = true;
    this.isEditingSignal = false;
    this.selectedSignalId = null;
    this.signalForm.reset();
    this.signalForm.patchValue({
      equipmentId: this.equipmentForm.get('equipmentId')?.value,
      tenant: this.equipmentForm.get('tenant')?.value
    });
  }

  loadSignals(): void {
    const equipmentId = this.equipmentForm.get('equipmentId')?.value;
    this.signalService.getSignalsByEquipmentId(equipmentId).subscribe(data => {
      this.signals.data = data.body || [];
      console.log("Signals caricati:", this.signals.data);
    });
  }

  saveSignal(): void {
    const equipmentId = this.equipmentForm.get('equipmentId')?.value;

    if (this.signalForm.valid) {
      const signalData = this.signalForm.getRawValue();

      if (this.isEditingSignal) {
        this.signalService.updateSignal(equipmentId, signalData.signalId, signalData).subscribe({
          next: () => {
            this.loadSignals();
            this.resetSignalForm();
          },
          error: (err) => console.error('Errore aggiornamento Signal:', err)
        });
      } else {
        this.signalService.saveSignal(equipmentId, signalData).subscribe({
          next: () => {
            this.loadSignals();
            this.resetSignalForm();
          },
          error: (err) => console.error('Errore salvataggio Signal:', err)
        });
      }
    }
  }

  editSignal(signal: any): void {
    this.isEditingSignal = true;
    this.showSignalForm = true;
    this.selectedSignalId = signal.signalId;
    this.signalForm.patchValue(signal);
  }

  resetSignalForm(): void {
    this.signalForm.reset();
    this.showSignalForm = false;
    this.isEditingSignal = false;
    this.selectedSignalId = null;
  }

  deleteSignal(equipmentId: string, signalId: string): void {
    if (!confirm("Sei sicuro di voler eliminare questo Signal?")) return;

    this.signalService.deleteSignalById(equipmentId, signalId).subscribe({
      next: () => {
        this.loadSignals();
      },
      error: (err) => {
        console.error("Errore durante l'eliminazione del Signal:", err);
      }
    });
  }

  togglePullerForm(): void {
    this.showPullerForm = true;
    this.isEditingPuller = false;
    this.selectedPullerId = null;
    this.pullerForm.reset();

    const equipmentId = this.equipmentForm.get('equipmentId')?.value;
    const tenant = this.equipmentForm.get('tenant')?.value;

    this.pullerForm.get('equipmentId')?.setValue(equipmentId);
    this.pullerForm.get('tenant')?.setValue(tenant);
  }

  loadPullers(): void {
    const equipmentId = this.equipmentForm.get('equipmentId')?.value;
    this.equipmentService.getPullersByEquipmentId(equipmentId).subscribe(data => {
      this.pullers.data = data.body || [];
      this.showPullers = true;
      this.showPullerForm = false;
    });
  }

  savePuller(): void {
    const equipmentId = this.equipmentForm.get('equipmentId')?.value;

    if (this.pullerForm.valid) {
      const pullerData = this.pullerForm.getRawValue();

      if (this.isEditingPuller) {
        this.equipmentService.updatePuller(equipmentId, this.selectedPullerId!, pullerData).subscribe({
          next: () => {
            this.loadPullers();
            this.resetPullerForm();
          },
          error: (err) => console.error('Errore aggiornamento Puller:', err)
        });
      } else {
        this.equipmentService.createEquipmentPuller(equipmentId, pullerData).subscribe({
          next: () => {
            this.loadPullers();
            this.resetPullerForm();
          },
          error: (err) => console.error('Errore creazione Puller:', err)
        });
      }
    }
  }

  editPuller(puller: any): void {
    this.isEditingPuller = true;
    this.showPullerForm = true;
    this.selectedPullerId = puller.pullerId;
    this.pullerForm.patchValue(puller);
  }

  resetPullerForm(): void {
    this.pullerForm.reset();
    this.showPullerForm = false;
    this.isEditingPuller = false;
    this.selectedPullerId = null;
  }

  deletePullerById(pullerId: string, index: number): void {
    if (!confirm("Sei sicuro di voler eliminare questo Puller?")) return;

    const equipmentId = this.equipmentForm.get('equipmentId')?.value;
    this.equipmentService.deletePullerById(equipmentId, pullerId).subscribe({
      next: () => {
        this.loadPullers();
      },
      error: (err) => {
        console.error("Errore durante l'eliminazione del Puller:", err);
      }
    });
  }

  togglePullerList(): void {
    this.showPullers = !this.showPullers;
  }

}
