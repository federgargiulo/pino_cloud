import { Component, Inject, OnInit, ViewEncapsulation } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialog } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import { EquipmentServices } from '../../../../service/equipment.service';
import { SignalServices } from '../../../../service/signal.service';
import { DeleteConfirmDialogComponent } from './delete-confirm-dialog.component';

interface Signal {
  signalId: string;
  name: string;
  equipmentId: string;
}

@Component({
  selector: 'app-equipment-confirm-dialog',
  templateUrl: './equipment-confirm-dialog.component.html',
  styleUrls: ['./equipment-confirm-dialog.component.scss'],
  standalone: false,
})
export class EquipmentConfirmDialogComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private equipmentService: EquipmentServices,
    private signalService: SignalServices,
    public dialogRef: MatDialogRef<EquipmentConfirmDialogComponent>,
    private dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: { equipmentId: string, message?: string }
  ) {}

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  onYesClick(): void {
    this.updateEquipment();
    this.dialogRef.close(true);
  }

  displayedColumns: string[] = ['select', 'signalId', 'name'];

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
  pullerSelection = new SelectionModel<any>(true, []);

  selection = new SelectionModel<Signal>(true, []);

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.signals.data.length;
    return numSelected === numRows;
  }

  toggleAllRows() {
    if (this.isAllSelected()) {
      this.selection.clear();
      return;
    }
    this.selection.select(...this.signals.data);
  }

  checkboxLabel(row?: Signal): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row`;
  }

  removeSelectedRows() {
    this.selection.selected.forEach(signal => {
      this.deleteSignal(signal.equipmentId, signal.signalId);
    });
    this.selection.clear();
  }

  editSelectedSignal() {
    if (this.selection.selected.length === 1) {
      this.editSignal(this.selection.selected[0]);
    }
  }

  ngOnInit(): void {

    this.equipmentForm = this.formBuilder.group({
      equipmentId: [{ value: '', disabled: true }],
      name: [''],
      tenant: [{ value: '', disabled: true }],
      status: [''],
      createdDttm: [{ value: '', disabled: true }],
      updateDttm: [{ value: '', disabled: true }]
    });
   this.equipmentId = this.data.equipmentId;
   this.loadEquipmentDetail(this.equipmentId);

    this.initSignalForm();
    this.initPullerForm();
  }

  private atLeastOneFieldRequired(controls: string[]): ValidationErrors | null {
    return (group: AbstractControl): ValidationErrors | null => {
      const hasValue = controls.some(controlName => {
        const control = group.get(controlName);
        return control && control.value && control.value.trim() !== '';
      });
      return hasValue ? null : { atLeastOneRequired: true };
    };
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
    }, { validators: this.atLeastOneFieldRequired(['unitOfMeasurement', 'name', 'minVal', 'maxVal', 'downRedLimit', 'downYellowLimit', 'upRedLimit', 'upYellowLimit']) });
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
    }, { validators: this.atLeastOneFieldRequired(['url', 'apiKey', 'intervalInSec']) });
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
      this.selection.clear();
    });
  }

  saveSignal(): void {
    const equipmentId = this.equipmentForm.get('equipmentId')?.value;
    if (this.signalForm.valid) {
      const signalData = this.signalForm.getRawValue();

      if (this.isEditingSignal) {
        console.log("this.signalForm.valid:", this.signalForm.valid);
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
    this.selection.clear();
  }

  deleteSignal(equipmentId: string, signalId: string): void {
    console.log('Equipment ID', equipmentId);
    console.log('Signal ID', signalId);
    const dialogRef = this.dialog.open(DeleteConfirmDialogComponent, {
      width: '400px',
      data: { itemType: 'signal' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.signalService.deleteSignalById(equipmentId, signalId).subscribe({
          next: () => {
            console.log('Signal deleted');
            this.loadSignals();
          },
          error: (err) => {
            console.error("Errore durante l'eliminazione del Signal:", err);
          }
        });
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
    const tenant = this.equipmentForm.get('tenant')?.value;

    // Assicurati che equipmentId e tenant siano sempre settati
    this.pullerForm.get('equipmentId')?.setValue(equipmentId);
    this.pullerForm.get('tenant')?.setValue(tenant);

    if (this.pullerForm.valid) {
      const pullerData = this.pullerForm.getRawValue();

      // Rimuovi il pullerId se è nullo o stringa vuota
      if (!pullerData.pullerId || pullerData.pullerId.trim() === '') {
        delete pullerData.pullerId;
      }

      console.info('createEquipmentPuller per equipmentId:', equipmentId, pullerData);

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
    } else {
      console.warn('Form non valido:', this.pullerForm.errors);
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
    this.pullerSelection.clear();
  }

  deletePullerById(pullerId: string, index: number): void {
    const dialogRef = this.dialog.open(DeleteConfirmDialogComponent, {
      width: '400px',
      data: { itemType: 'puller' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
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
    });
  }

  togglePullerList(): void {
    this.showPullers = !this.showPullers;
  }

  isAllPullersSelected() {
    const numSelected = this.pullerSelection.selected.length;
    const numRows = this.pullers.data.length;
    return numSelected === numRows;
  }

  toggleAllPullerRows() {
    if (this.isAllPullersSelected()) {
      this.pullerSelection.clear();
      return;
    }
    this.pullerSelection.select(...this.pullers.data);
  }

  pullerCheckboxLabel(row?: any): string {
    if (!row) {
      return `${this.isAllPullersSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.pullerSelection.isSelected(row) ? 'deselect' : 'select'} row`;
  }

  editSelectedPuller() {
    if (this.pullerSelection.selected.length === 1) {
      this.editPuller(this.pullerSelection.selected[0]);
    }
  }

  deleteSelectedPullers() {
    const dialogRef = this.dialog.open(DeleteConfirmDialogComponent, {
      width: '400px',
      data: { itemType: 'puller' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.pullerSelection.selected.forEach(puller => {
          this.deletePullerById(puller.pullerId, 0);
        });
        this.pullerSelection.clear();
      }
    });
  }
}
