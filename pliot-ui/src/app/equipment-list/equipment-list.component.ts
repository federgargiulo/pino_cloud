import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Equipment } from '../equipment';


@Component({
  selector: 'app-equipment-list',
  standalone: true,
  imports: [CommonModule  ],
  templateUrl: './equipment-list.component.html',
  styleUrl: './equipment-list.component.css'
})
export class EquipmentListComponent {
  @Input() equipment!: Equipment;

}
