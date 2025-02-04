import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { EquipmentDetailComponent } from './equipment-detail/equipment-detail.component';

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
       path: 'home',
       component: EquipmentDetailComponent,
       title: 'Home details'
     }
];

export default routeConfig;
