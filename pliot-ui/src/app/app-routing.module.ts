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
import { SearchTenantComponent } from './feature/tenant/search-tenant/search-tenant.component';
import { AddTenantComponent } from './feature/tenant/add-tenant/add-tenant.component';
import { DetailTenantComponent } from './feature/tenant/detail-tenant/detail-tenant.component';
import { SearchUserComponent } from './feature/user/search-user/search-user.component';
import { DetailUserComponent } from './feature/user/detail-user/detail-user.component';
import { PivotTableComponent } from './olap/pivot-table/pivot-table.component';
import { ProfileComponent } from './feature/profile/profile.component';
import { MonitorComponent } from './feature/system/monitor/monitor.component';

const routes: Routes = [

  { path: '', component: HomeComponent, title: 'Home' },
  { path: 'help-angular', component: HelpAngularComponent, title: 'Help Angular' },
  { path: 'add-equipment', component: AddEquipmentComponent, title: 'Add Equipment' },
  { path: 'search-equipment', component: SearchEquipmentComponent, title: 'Search Equipment' },
  { path: 'detail-equipment/:id', component: DetailEquipmentComponent, title: 'Detail Equipment' ,  data: { renderMode: 'ssr' }},
  { path: 'userdashboard-list', component: UserdashboardListComponent, title: 'Dashboards List'},
  { path: 'userdashboard-detail/:id', component: UserdashboardViewComponent, title: ' Dashboards Detail' , data: { renderMode: 'ssr' } },
  { path: 'userdashboard-new', component: UserdashboardViewComponent, title: 'new Dashboards'},
  { path: 'dash-measure/:id', component: DashMeasureComponent, title: 'View Dashboards', data: { renderMode: 'ssr' } },
  { path: 'search-tenant', component: SearchTenantComponent, title: 'Search Tenant' },
  { path: 'add-tenant', component: AddTenantComponent, title: 'Add Tenant' },
  { path: 'detail-tenant/:id', component: DetailTenantComponent, title: 'Detail Tenant' , data: { renderMode: 'ssr' } },
  { path: 'search-users', component: SearchUserComponent, title: 'Search Users' },
  { path: 'add-users', component: DetailUserComponent, title: 'Add Users' },
  { path: 'detail-user/:id', component: DetailUserComponent, title: 'Modifica Users' },
  { path: 'olap', component: PivotTableComponent, title: 'Modifica Users' },
  { path: 'profile', component: ProfileComponent, title: 'Profile' },
  { path: 'system-status', component: MonitorComponent, title: 'Status' }

];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {



}
