import { Component, ViewChild } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from './service/user.service';
import { filter } from 'rxjs/operators';
import { MatDrawer } from '@angular/material/sidenav';

@Component({
  selector: 'app-root',
  standalone: false,
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  @ViewChild('drawer') drawer!: MatDrawer;

  showTenantItems = false;
  showEquipmentItems = false;
  showUserItems = false;
  showDashboardItems = false;
  showAccountItems = false;
  title = 'pliot-ui';

  constructor(private router: Router, private modalService: NgbModal, private userService: UserService) {
    // Log iniziale dello stato
    console.log('Stato iniziale dei pannelli:', {
      tenant: this.showTenantItems,
      equipment: this.showEquipmentItems,
      user: this.showUserItems,
      dashboard: this.showDashboardItems
    });

    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: any) => {
      const url = event.urlAfterRedirects;
      console.log('URL corrente:', url);

      // Update panel states based on the URL
      this.showTenantItems = url.includes('/search-tenant') || url.includes('/add-tenant');
      this.showEquipmentItems = url.includes('/search-equipment') || url.includes('/add-equipment');
      this.showUserItems = url.includes('/search-users') || url.includes('/add-users');
      this.showDashboardItems = url.includes('/userdashboard-list') || url.includes('/userdashboard-new');

      // Log dello stato dopo l'aggiornamento
      console.log('Stato dei pannelli dopo navigazione:', {
        tenant: this.showTenantItems,
        equipment: this.showEquipmentItems,
        user: this.showUserItems,
        dashboard: this.showDashboardItems
      });
    });
  }

  public open(modal: any): void {
    this.modalService.open(modal);
  }

  public hasRole(role: string): boolean {
    return this.userService.hasRole(role);
  }

  public isPliotAdmin(): boolean {
    return this.userService.hasRole("pliot_admin");
  }

  toggleDrawer(section: string): void {
    const currentUrl = this.router.url;
    console.log('Toggle drawer chiamato per sezione:', section);
    console.log('URL corrente:', currentUrl);

    let isActive = false;

    // Check if the section has an active route
    switch (section) {
      case 'tenant':
        isActive = currentUrl.includes('/search-tenant') || currentUrl.includes('/add-tenant');
        break;
      case 'equipment':
        isActive = currentUrl.includes('/search-equipment') || currentUrl.includes('/add-equipment');
        break;
      case 'user':
        isActive = currentUrl.includes('/search-users') || currentUrl.includes('/add-users');
        break;
      case 'dashboard':
        isActive = currentUrl.includes('/userdashboard-list') || currentUrl.includes('/userdashboard-new');
        break;
    }

    console.log('La sezione è attiva?', isActive);

    // Toggle the selected section
    switch (section) {
      case 'tenant':
        this.showTenantItems = !this.showTenantItems;
        if (!isActive) {
          this.showEquipmentItems = false;
          this.showUserItems = false;
          this.showDashboardItems = false;
        }
        break;
      case 'equipment':
        this.showEquipmentItems = !this.showEquipmentItems;
        if (!isActive) {
          this.showTenantItems = false;
          this.showUserItems = false;
          this.showDashboardItems = false;
        }
        break;
      case 'user':
        this.showUserItems = !this.showUserItems;
        if (!isActive) {
          this.showTenantItems = false;
          this.showEquipmentItems = false;
          this.showDashboardItems = false;
        }
        break;
      case 'dashboard':
        this.showDashboardItems = !this.showDashboardItems;
        if (!isActive) {
          this.showTenantItems = false;
          this.showEquipmentItems = false;
          this.showUserItems = false;
        }
        break;
    }

    // Log dello stato finale
    console.log('Stato finale dei pannelli:', {
      tenant: this.showTenantItems,
      equipment: this.showEquipmentItems,
      user: this.showUserItems,
      dashboard: this.showDashboardItems
    });
  }
}
