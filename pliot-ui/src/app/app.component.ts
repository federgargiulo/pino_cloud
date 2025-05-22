import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from './service/user.service';

@Component({
  selector: 'app-root',
  standalone: false,
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  showUserItems = false;
  showEquipmentItems = false;
  showTenantItems = false;
  showDashboardItems = false;
  showAccountItems = false;
  title = 'pliot-ui';

  constructor( private modalService: NgbModal , private userService: UserService ) {

  }

  public open(modal: any): void {
    this.modalService.open(modal);
  }

  public hasRole( role:string ){
    return this.userService.hasRole( role );
  }

  public isPliotAdmin(){
    return this.userService.hasRole( "pliot_admin" );
  }

  toggleDrawer(drawer: 'user' | 'equipment' | 'tenant' | 'dashboard'): void {
    // Chiudi tutti i drawer
    this.showUserItems = false;
    this.showEquipmentItems = false;
    this.showTenantItems = false;
    this.showDashboardItems = false;

    // Apri solo il drawer selezionato
    switch (drawer) {
      case 'user':
        this.showUserItems = true;
        break;
      case 'equipment':
        this.showEquipmentItems = true;
        break;
      case 'tenant':
        this.showTenantItems = true;
        break;
      case 'dashboard':
        this.showDashboardItems = true;
        break;
    }
  }
}
