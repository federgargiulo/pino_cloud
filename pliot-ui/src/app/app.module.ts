import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HelpAngularComponent } from './common/help-angular/help-angular.component';
import { HeaderComponent } from './common/header/header.component';
import { HomeComponent } from './common/home/home.component';
import { AddEquipmentComponent } from './feature/equipment/add-equipment/add-equipment.component';
import {  provideHttpClient } from '@angular/common/http';

import { SearchEquipmentComponent } from './feature/equipment/search-equipment/search-equipment.component';
import { DashMeasureComponent } from './dashboard/dash-measure/dash-measure.component';

import { DetailEquipmentComponent } from './feature/equipment/detail-equipment/detail-equipment.component';
import { UserdashboardListComponent } from './dashboard/userdashboard-list/userdashboard-list.component';
import { UserdashboardViewComponent } from './dashboard/userdashboard-view/userdashboard-view.component';


@NgModule({
  declarations: [
    AppComponent,
    HelpAngularComponent,
    HeaderComponent,
    HomeComponent,
    AddEquipmentComponent,
    SearchEquipmentComponent,
    DashMeasureComponent,
    DetailEquipmentComponent,
    UserdashboardListComponent,
    UserdashboardViewComponent,



  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule, // Aggiunto per i form

  ],
  providers: [
    provideClientHydration(withEventReplay()), provideHttpClient()

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
