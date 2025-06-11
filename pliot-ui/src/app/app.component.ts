import { Component, ViewChild, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from './service/user.service';
import { filter } from 'rxjs/operators';
import { MatDrawer } from '@angular/material/sidenav';

interface MenuItem {
  id: string;
  label: string;
  subItems?: { id: string; label: string }[];
}

@Component({
  selector: 'app-root',
  standalone: false,
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  @ViewChild('drawer') drawer!: MatDrawer;

  showTenantItems = false;
  showEquipmentItems = false;
  showUserItems = false;
  showDashboardItems = false;
  showAccountItems = false;
  title = 'pliot-ui';
  searchText = '';
  issuer: string | null = '';

  menuItems: MenuItem[] = [
    { id: 'home', label: 'Home' },
    {
      id: 'tenant',
      label: 'Tenant',
      subItems: [
        { id: 'search-tenant', label: 'Search Tenant' },
        { id: 'add-tenant', label: 'Create Tenant' }
      ]
    },
    {
      id: 'equipment',
      label: 'Equipment',
      subItems: [
        { id: 'search-equipment', label: 'Search Equipment' },
        { id: 'add-equipment', label: 'Create Equipment' }
      ]
    },
    {
      id: 'users',
      label: 'Users',
      subItems: [
        { id: 'search-users', label: 'Search Users' },
        { id: 'add-users', label: 'Create Users' }
      ]
    },
    {
      id: 'dashboard',
      label: 'Dashboard',
      subItems: [
        { id: 'userdashboard-list', label: 'List Dashboard' },
        { id: 'userdashboard-new', label: 'View Dashboard' }
      ]
    },
    { id: 'system-status', label: 'System Status' }
  ];

  visibleMenuItems: { [key: string]: boolean } = {};

  constructor(private router: Router, private modalService: NgbModal, private userService: UserService) {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: any) => {
      const url = event.urlAfterRedirects;
      this.updateActivePanel(url);
    });
  }

  ngOnInit() {
    console.log('Initial panel states:', {
      tenant: this.showTenantItems,
      equipment: this.showEquipmentItems,
      user: this.showUserItems,
      dashboard: this.showDashboardItems
    });
    this.initializeVisibleMenuItems();
    this.updateActivePanel(this.router.url);
  }

  private initializeVisibleMenuItems() {
    this.menuItems.forEach(item => {
      this.visibleMenuItems[item.id] = true;
      if (item.subItems) {
        item.subItems.forEach(subItem => {
          this.visibleMenuItems[subItem.id] = true;
        });
      }
    });
  }

  private updateActivePanel(url: string) {
    // Resetta tutti i pannelli
    this.showTenantItems = false;
    this.showEquipmentItems = false;
    this.showUserItems = false;
    this.showDashboardItems = false;

    // Apri il pannello corrispondente all'URL corrente
    if (url.includes('/search-tenant') || url.includes('/add-tenant')) {
      this.showTenantItems = true;
    } else if (url.includes('/search-equipment') || url.includes('/add-equipment')) {
      this.showEquipmentItems = true;
    } else if (url.includes('/search-users') || url.includes('/add-users')) {
      this.showUserItems = true;
    } else if (url.includes('/userdashboard-list') || url.includes('/userdashboard-new')) {
      this.showDashboardItems = true;
    }

    console.log('Updated panel states:', {
      tenant: this.showTenantItems,
      equipment: this.showEquipmentItems,
      user: this.showUserItems,
      dashboard: this.showDashboardItems
    });
  }

  toggleDrawer(section: string) {
    console.log('Toggling section:', section);
    console.log('Current URL:', this.router.url);

    const currentUrl = this.router.url;
    let isActiveSection = false;

    // Verifica se la sezione è attiva
    switch (section) {
      case 'tenant':
        isActiveSection = currentUrl.includes('/search-tenant') || currentUrl.includes('/add-tenant');
        if (!isActiveSection) {
          this.showTenantItems = !this.showTenantItems;
          if (this.showTenantItems) {
            this.showEquipmentItems = false;
            this.showUserItems = false;
            this.showDashboardItems = false;
          }
        }
        break;
      case 'equipment':
        isActiveSection = currentUrl.includes('/search-equipment') || currentUrl.includes('/add-equipment');
        if (!isActiveSection) {
          this.showEquipmentItems = !this.showEquipmentItems;
          if (this.showEquipmentItems) {
            this.showTenantItems = false;
            this.showUserItems = false;
            this.showDashboardItems = false;
          }
        }
        break;
      case 'user':
        isActiveSection = currentUrl.includes('/search-users') || currentUrl.includes('/add-users');
        if (!isActiveSection) {
          this.showUserItems = !this.showUserItems;
          if (this.showUserItems) {
            this.showTenantItems = false;
            this.showEquipmentItems = false;
            this.showDashboardItems = false;
          }
        }
        break;
      case 'dashboard':
        isActiveSection = currentUrl.includes('/userdashboard-list') || currentUrl.includes('/userdashboard-new');
        if (!isActiveSection) {
          this.showDashboardItems = !this.showDashboardItems;
          if (this.showDashboardItems) {
            this.showTenantItems = false;
            this.showEquipmentItems = false;
            this.showUserItems = false;
          }
        }
        break;
    }

    console.log('Final panel states:', {
      tenant: this.showTenantItems,
      equipment: this.showEquipmentItems,
      user: this.showUserItems,
      dashboard: this.showDashboardItems
    });
  }

  public open(modal: any): void {
    this.modalService.open(modal);
  }

  public hasRole(role: string): boolean {
    return this.userService.hasRole(role);
  }

  isPliotAdmin(): boolean {
    return this.userService.hasRole("pliot_admin");
  }

  filterMenuItems() {
    const searchLower = this.searchText.toLowerCase();

    this.menuItems.forEach(item => {
      const mainItemVisible = this.matchesSearch(item.label, searchLower);
      let hasVisibleSubItems = false;

      if (item.subItems) {
        item.subItems.forEach(subItem => {
          const subItemVisible = this.matchesSearch(subItem.label, searchLower);
          this.visibleMenuItems[subItem.id] = subItemVisible;
          if (subItemVisible) {
            hasVisibleSubItems = true;
          }
        });
      }

      // Mostra l'elemento principale se corrisponde alla ricerca o se ha sottomenu visibili
      this.visibleMenuItems[item.id] = mainItemVisible || hasVisibleSubItems;

      // Se c'è una corrispondenza nella ricerca, espandi automaticamente il sottomenu
      if (mainItemVisible || hasVisibleSubItems) {
        switch (item.id) {
          case 'tenant':
            this.showTenantItems = true;
            break;
          case 'equipment':
            this.showEquipmentItems = true;
            break;
          case 'users':
            this.showUserItems = true;
            break;
          case 'dashboard':
            this.showDashboardItems = true;
            break;
        }
      }
    });
  }

  isMenuItemVisible(item: string): boolean {
    return this.visibleMenuItems[item] || false;
  }

  private matchesSearch(text: string, search: string): boolean {
    return text.toLowerCase().includes(search);
  }

  clearSearch() {
    this.searchText = '';
    this.initializeVisibleMenuItems();
    this.updateActivePanel(this.router.url);
  }

  navigateToProfile() {
    this.router.navigate(['/profile']);
  }

  logout(){
    const token = this.userService.keycloak.token;
    const idToken = this.userService.keycloak.idToken;
    console.log('idToken:', idToken);
    if (!token || !idToken ) {
      console.error('Token non disponibile');
      return;
    }
    const redirectUri = encodeURIComponent(window.location.origin);
    const iss=this.issuer;
    //const logoutUrl = iss+'/protocol/openid-connect/logout?redirect_uri='+redirectUri;

    const logoutUrl = iss+'/protocol/openid-connect/logout?id_token_hint='+idToken+'&post_logout_redirect_uri='+redirectUri;

    console.log('logoutUrl:', logoutUrl);
    window.location.href = logoutUrl;
  }
}
