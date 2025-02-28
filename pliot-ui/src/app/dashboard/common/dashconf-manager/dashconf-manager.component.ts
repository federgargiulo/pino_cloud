import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
 
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { json } from 'stream/consumers';
 

@Component({
  selector: 'app-dashconf-manager',
  standalone: false,
  templateUrl: './dashconf-manager.component.html',
  styleUrl: './dashconf-manager.component.css'
})
export class DashconfManagerComponent implements OnChanges  {

  signalForm: FormGroup;
  @Input() initialSignals: string = '';
  @Output() jsonChanged = new EventEmitter<string>();

  constructor(private fb: FormBuilder) {
    this.signalForm = this.fb.group({
      signals: this.fb.array([]) // Array di segnali
    });

    this.signalForm.valueChanges.subscribe(() => {
      this.emitJson();
    });
   
  }

  ngOnChanges(changes: SimpleChanges) {
    // Quando il JSON cambia, ricarica il FormArray
    
    if (changes['initialSignals'] && this.initialSignals) {
      this.loadSignals(JSON.parse(this.initialSignals));
    }
  }

  loadSignals(data: any) {
    const signalsArray = data.signals || [];

    // Pulisce il FormArray esistente
    while (this.signals.length) {
      this.signals.removeAt(0);
    }

    // Aggiungi ogni segnale come FormGroup
    signalsArray.forEach((signal: any) => {
      this.signals.push(this.fb.group({
        signalId: [signal.signalId, Validators.required],
        tipo: [signal.tipo, Validators.required]
      }));
    });

    // Emetti il JSON
    this.emitJson();
  }

  emitJson() {
    const jsonString = JSON.stringify(this.signalForm.value);
    this.jsonChanged.emit(jsonString);
  }
  

  // Getter per il FormArray
  get signals() {
    return this.signalForm.get('signals') as FormArray;
  }

  // Aggiunge un nuovo segnale
  addSignal() {
    const signalGroup = this.fb.group({
      signalId: [null, Validators.required], // ID del segnale
      tipo: ['', Validators.required]        // Tipo di segnale
    });

    this.signals.push(signalGroup);
  }

  // Rimuove un segnale dalla lista
  removeSignal(index: number) {
    this.signals.removeAt(index);
  }

  // Mostra il JSON risultante
  submit() {
    console.log('JSON Generato:', JSON.stringify(this.signalForm.value, null, 2));
  }

}
