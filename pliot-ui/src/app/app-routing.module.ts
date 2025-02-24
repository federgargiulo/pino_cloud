import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HelpAngularComponent } from './common/help-angular/help-angular.component';
import { HomeComponent } from './common/home/home.component';
import { AddEquipmentComponent } from './feature/equipment/add-equipment/add-equipment.component';
import { SearchEquipmentComponent } from './feature/equipment/search-equipment/search-equipment.component';
import { DashMeasureComponent } from './dashboard/dash-measure/dash-measure.component';
import { DetailEquipmentComponent } from './feature/equipment/detail-equipment/detail-equipment.component';
import { UserdashboardListComponent } from './dashboard/userdashboard-list/userdashboard-list.component';
import { UserdashboardViewComponent } from './dashboard/userdashboard-view/userdashboard-view.component';

const routes: Routes = [

  { path: '', component: HomeComponent, title: 'Home' },
  { path: 'help-angular', component: HelpAngularComponent, title: 'Help Angular' },
  { path: 'add-equipment', component: AddEquipmentComponent, title: 'Add Equipment' },
  { path: 'search-equipment', component: SearchEquipmentComponent, title: 'Search Equipment' },
  { path: 'dash-measure', component: DashMeasureComponent, title: 'Search Equipment' },
  { path: 'detail-equipment/:id', component: DetailEquipmentComponent, title: 'Detail Equipment'},
  { path: 'userdashboard-list', component: UserdashboardListComponent, title: 'Detail Equipment'},
  { path: 'userdashboard-new', component: UserdashboardViewComponent, title: 'Detail Equipment'},
  
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {



}
