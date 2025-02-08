import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HelpAngularComponent } from './common/help-angular/help-angular.component';
import { HomeComponent } from './common/home/home.component';

const routes: Routes = [
  { path: 'help-angular', component: HelpAngularComponent, title: 'Help Angular' },
  { path: '', component: HomeComponent, title: 'Home' },
   
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
