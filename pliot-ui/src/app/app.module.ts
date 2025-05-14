import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import {
  BrowserModule,
  provideClientHydration,
  withEventReplay,
} from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HelpAngularComponent } from './common/help-angular/help-angular.component';
import { HeaderComponent } from './common/header/header.component';
import { HomeComponent } from './common/home/home.component';
import { AddEquipmentComponent } from './feature/equipment/add-equipment/add-equipment.component';
import {
  withInterceptorsFromDi,
  provideHttpClient,
  withInterceptors,
} from '@angular/common/http';
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
import {
  AutoRefreshTokenService,
  createInterceptorCondition,
  INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
  IncludeBearerTokenCondition,
  includeBearerTokenInterceptor,
  provideKeycloak,
  UserActivityService,
  withAutoRefreshToken,
} from 'keycloak-angular';
import { logInterceptor } from './interceptors/log.interceptor';
import { SearchUserComponent } from './feature/user/search-user/search-user.component';
import { DetailUserComponent } from './feature/user/detail-user/detail-user.component';
import { ConfigurationService } from './service/config.service.';
import { OlapModule } from './olap/olap.module';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ProfileComponent } from './feature/profile/profile.component';
import { MatTableModule } from '@angular/material/table';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDividerModule } from '@angular/material/divider';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import {
  MatDialogActions,
  MatDialogContent,
  MatDialogModule,
} from '@angular/material/dialog';
import { ConfirmDialogModule } from './feature/user/search-user/confirm-dialog/confirm-dialog.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';

var loc = new ConfigurationService();

export const KEYCLOAK_PRIVIDER = () =>
  provideKeycloak({
    config: {
      url: loc.getIDPUrl(),
      realm: loc.getRealm(),
      clientId: loc.getClientId(),
    },
    initOptions: {
      onLoad: 'login-required',
      redirectUri: loc.getRedirectUri(),
      silentCheckSsoRedirectUri: loc.getSilentCheckSsoRedirectUri(),
      checkLoginIframe: false,
    },
    features: [
      withAutoRefreshToken({
        onInactivityTimeout: 'logout',
        sessionTimeout: 60000,
      }),
    ],
    providers: [AutoRefreshTokenService, UserActivityService],
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
    ProfileComponent,
  ],
  imports: [
    BrowserModule,
    CommonModule,
    RouterModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatToolbarModule,
    MatTableModule,
    MatCheckboxModule,
    MatDividerModule,
    MatDialogModule,
    MatDialogContent,
    MatDialogActions,
    OlapModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ConfirmDialogModule,
  ],
  providers: [
    KEYCLOAK_PRIVIDER(),
    provideClientHydration(withEventReplay()),
    provideHttpClient(withInterceptors([addBearer, logInterceptor])),
    DatePipe,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  bootstrap: [AppComponent],
})
export class AppModule {}
