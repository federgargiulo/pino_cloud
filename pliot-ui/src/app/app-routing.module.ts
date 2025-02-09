import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HelpAngularComponent } from './common/help-angular/help-angular.component';
import { HomeComponent } from './common/home/home.component';
import { AddEquipmentComponent } from './feature/equipment/add-equipment/add-equipment.component';
import { SearchEquipmentComponent } from './feature/equipment/search-equipment/search-equipment.component';

const routes: Routes = [
  { path: 'help-angular', component: HelpAngularComponent, title: 'Help Angular' },
  { path: 'add-equipment', component: AddEquipmentComponent, title: 'Add Equipment' },
  { path: 'search-equipment', component: SearchEquipmentComponent, title: 'Search Equipment' },
  { path: '', component: HomeComponent, title: 'Home' },
   
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
