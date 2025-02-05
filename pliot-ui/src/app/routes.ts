import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { EquipmentDetailComponent } from './equipment/equipment-detail.component';
import { EquipmentListComponent } from './equipment/equipment-list.component'

const routeConfig: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: 'Home page'
  },
  {
    path: 'equipment-details/:id',
    component: EquipmentDetailComponent,
    title: 'Home details'
  },
  {
    path: 'equipment',
    component: EquipmentListComponent,
    title: 'Equipments'
  }
];

export default routeConfig;
