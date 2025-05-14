import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

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

  constructor(private modalService: NgbModal, private router: Router) {}

  public open(modal: any): void {
    this.modalService.open(modal);
  }

  isTenantRouteActive(): boolean {
    return this.router.isActive('/search-tenant', false) ||
           this.router.isActive('/add-tenant', false);
  }

  isEquipmenttRouteActive(): boolean {
    return this.router.isActive('/search-equipment', false) ||
           this.router.isActive('/add-equipment', false);
  }

  isUserItemRouteActive(): boolean {
    return this.router.isActive('/search-users', false) ||
           this.router.isActive('/add-users', false);
  }

  isDashboardRouteActive(): boolean {
    return this.router.isActive('/userdashboard-list', false) ||
           this.router.isActive('/userdashboard-new', false);
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
