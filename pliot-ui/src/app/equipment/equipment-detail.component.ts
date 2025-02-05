
import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-equipment-detail',
  standalone: true,
  imports: [],
  templateUrl: './equipment-detail.component.html',
  styleUrl: './equipment-detail.component.css'
})
export class EquipmentDetailComponent {
    route: ActivatedRoute = inject(ActivatedRoute);
    equipmentId = "";
    constructor() {
        this.equipmentId = this.route.snapshot.params['id'];
    }
}
