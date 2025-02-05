
import { Component , inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Equipment } from '../data/equipment';
import { RouterModule } from '@angular/router';
import { EquipmentService } from '../service/equipment.service';

@Component({
  selector: 'app-equipment-list',
  standalone: true,
  imports: [CommonModule,
    RouterModule
    ],
  templateUrl: './equipment-list.component.html',
  styleUrl: './equipment-list.component.css'
})
export class EquipmentListComponent {
  equipmentService: EquipmentService = inject(EquipmentService);


      equipments: Equipment[] =[]

      constructor() {
        this.equipmentService.getAllEquipments().then((equipments: Equipment[]) => {
          this.equipments = equipments;
        });
      }



}
