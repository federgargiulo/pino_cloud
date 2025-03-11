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
import {  withInterceptorsFromDi , provideHttpClient, withInterceptors } from '@angular/common/http';

import { SearchEquipmentComponent } from './feature/equipment/search-equipment/search-equipment.component';
import { DashMeasureComponent } from './dashboard/dash-measure/dash-measure.component';

import { DetailEquipmentComponent } from './feature/equipment/detail-equipment/detail-equipment.component';
import { UserdashboardListComponent } from './dashboard/userdashboard-list/userdashboard-list.component';
import { UserdashboardViewComponent } from './dashboard/userdashboard-view/userdashboard-view.component';
import { SearchTenantComponent } from './feature/tenant/search-tenant/search-tenant.component';
import { AddTenantComponent } from './feature/tenant/add-tenant/add-tenant.component';
import { DetailTenantComponent } from './feature/tenant/detail-tenant/detail-tenant.component';
import { DatePipe } from '@angular/common';
import { DashconfManagerComponent } from './dashboard/common/dashconf-manager/dashconf-manager.component';

import { addBearer } from './interceptors/auth.interceptor';
import { AutoRefreshTokenService, createInterceptorCondition, INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG, IncludeBearerTokenCondition, includeBearerTokenInterceptor, provideKeycloak, UserActivityService, withAutoRefreshToken } from 'keycloak-angular';
import { EnvironmentProviders }  from '@angular/core';
import { logInterceptor  } from './interceptors/log.interceptor';

import { SearchUserComponent } from './feature/user/search-user/search-user.component';
import { DetailUserComponent } from './feature/user/detail-user/detail-user.component';
import { LocUtilsService } from './loc-utils.service';


var loc = new LocUtilsService (); 

export const  KEYCLOAK_PRIVIDER = () => provideKeycloak({
  config: {
    url: loc.getIDPUrl() ,   // URL del server Keycloak
    realm: loc.getRealm()  ,                 // Nome del realm
    clientId: loc.getClientId() ,           // Client ID registrato su Keycloak
  },
    initOptions: {
      onLoad: 'login-required',  // Oppure 'check-sso' se non vuoi forzare il login
        redirectUri: window.location.origin, // Assicura che sia corretto
        silentCheckSsoRedirectUri: window.location.origin + '/assets/silent-check-sso.html',
        checkLoginIframe: false,
    },
    features: [
      withAutoRefreshToken({
        onInactivityTimeout: 'logout',
        sessionTimeout: 60000
      })
    ],
    providers: [AutoRefreshTokenService, UserActivityService]
  });

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
    SearchTenantComponent,
    AddTenantComponent,
    DetailTenantComponent,
    DashconfManagerComponent,
    SearchUserComponent,
    DetailUserComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule, // Aggiunto per i form
  ],
  providers: [
    KEYCLOAK_PRIVIDER(),
    provideClientHydration(withEventReplay()),
    provideHttpClient( withInterceptors([addBearer , logInterceptor ]) )
     ,DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
