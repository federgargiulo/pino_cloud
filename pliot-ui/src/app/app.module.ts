import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HelpAngularComponent } from './common/help-angular/help-angular.component';
import { HeaderComponent } from './common/header/header.component';
import { HomeComponent } from './common/home/home.component';
import { AddEquipmentComponent } from './feature/equipment/add-equipment/add-equipment.component';
import { HttpHeaders, HttpClient, provideHttpClient } from '@angular/common/http';

import { SearchEquipmentComponent } from './feature/equipment/search-equipment/search-equipment.component';


@NgModule({
  declarations: [
    AppComponent,
    HelpAngularComponent,
    HeaderComponent,
    HomeComponent,
    AddEquipmentComponent,
    SearchEquipmentComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
   
  ],
  providers: [
    provideClientHydration(withEventReplay()), provideHttpClient()
     
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
