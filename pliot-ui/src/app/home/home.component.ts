import { Component , inject } from '@angular/core';
import { EquipmentListComponent } from '../equipment-list/equipment-list.component';
import { Equipment } from '../data/equipment';
import { CommonModule } from '@angular/common';
import { EquipmentService } from '../service/equipment.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [EquipmentListComponent ,CommonModule ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
    readonly baseUrl = 'https://angular.io/assets/images/tutorials/faa';

    equipmentService: EquipmentService = inject(EquipmentService);


    equipments: Equipment[] =[]

    constructor() {
      this.equipmentService.getAllEquipments().then((equipments: Equipment[]) => {
        this.equipments = equipments;
      });
    }


}
