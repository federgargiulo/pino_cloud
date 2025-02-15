import { Component, Input, OnInit } from '@angular/core';
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
  equipmentDetail: any; // Dati dell'attrezzatura ricevuti dal backend
   loading = false;

  constructor(
     private route: ActivatedRoute,
     private equipmentService: EquipmentServices // Iniettiamo il servizio
   ) {}


 ngOnInit(): void {
     this.route.paramMap.subscribe(params => {
       this.equipmentId = params.get('id') || ''; // Assicura che non sia null
       console.log("Equipment ID ricevuto:", this.equipmentId);

       // Chiamata al backend per ottenere i dettagli
       this.loadEquipmentDetail(this.equipmentId);
     });
   }

   loadEquipmentDetail(id: string): void {
     this.equipmentService.getEquipmentById(id).subscribe({
       next: (data) => {

         this.equipmentDetail = data.body || data; // Salva il body della risposta // Salva i dati per visualizzarli nella UI
         console.log("Dati ricevuti dal server:", this.equipmentDetail);

       },
       error: (err) => {
         console.error("Errore nel caricamento dei dettagli:", err);
       }
     });
   }
}
