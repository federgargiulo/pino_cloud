import { Component, ViewChild, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from './service/user.service';
import { filter } from 'rxjs/operators';
import { MatDrawer } from '@angular/material/sidenav';
import { ConfigurationService } from './service/config.service.';

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

  showEdgeItems = false;
  showTenantItems = false;
  showEquipmentItems = false;
  showUserItems = false;
  showDashboardItems = false;
  showAccountItems = false;
  title = 'pliot-ui';
  searchText = '';
  issuer: string | null = '';
  fullName: string = '';
  initial: string = '';
  userRole: string = '';
  userGroupNames: string[] = [];


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
        { id: 'userdashboard-new', label: 'Create Dashboard' },
        { id: 'olap' , label : 'Search a Signal ' }
      ]
    },
    {
      id: 'edge',
      label: 'Edge',
      subItems: [
        { id: 'edge-list', label: 'Edge List' },
        { id: 'edge-detail', label: 'Edge Detail' }
      ]
    },
    { id: 'system-status', label: 'System Status' }
  ];

  visibleMenuItems: { [key: string]: boolean } = {};

  constructor(private router: Router,
    private modalService: NgbModal,
    private userService: UserService ,
    private config : ConfigurationService ) {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: any) => {
      const url = event.urlAfterRedirects;
      this.updateActivePanel(url);
    });
  }

  isServerMode( ){
    return "SERVER" == this.config.getConfig().mode
  }

  ngOnInit() {
    console.log('Initial panel states:', {
      tenant: this.showTenantItems,
      equipment: this.showEquipmentItems,
      user: this.showUserItems,
      dashboard: this.showDashboardItems
    });
    const firstName = this.userService.getCurrentFirstName() || '';
    const lastName = this.userService.getCurrentLastName() || '';
    this.fullName = `${firstName} ${lastName}`.trim();
    this.initial = firstName ? firstName.charAt(0).toUpperCase() : '';
    const groups = this.userService.getCurrentUserGroups();
    this.userRole = groups.join(', ');
    console.log('Token completo:', this.userService.keycloak.tokenParsed);
    this.issuer = this.userService.keycloak?.issuer || '';

    this.userService.getCurrentUserFromBackend().subscribe(response => {
      const user = response?.body;
      if (user) {
        this.fullName = `${user.firstName} ${user.lastName}`.trim();
        this.initial = user.firstName?.charAt(0).toUpperCase() || '';

        const roles = (user.usrGrp || []).map((grp: any) => grp.description);
        this.userRole = roles.join(', ');
        this.userGroupNames = (user.usrGrp || []).map((grp: any) => grp.grpName);
      } else {
        console.warn('Dati utente non disponibili');
      }
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
    // Verifica quali pannelli hanno voci attive
    const hasActiveTenant = url.includes('/search-tenant') || url.includes('/add-tenant');
    const hasActiveEquipment = url.includes('/search-equipment') || url.includes('/add-equipment');
    const hasActiveUser = url.includes('/search-users') || url.includes('/add-users');
    const hasActiveDashboard = url.includes('/userdashboard-list') || url.includes('/userdashboard-new');
    const hasActiveEdge = url.includes('/edge-list') || url.includes('/edge-detail');

    // Mantieni aperti i pannelli con voci attive
    if (hasActiveTenant) {
      this.showTenantItems = true;
    }
    if (hasActiveEquipment) {
      this.showEquipmentItems = true;
    }
    if (hasActiveUser) {
      this.showUserItems = true;
    }
    if (hasActiveDashboard) {
      this.showDashboardItems = true;
    }

    if (hasActiveEdge) {
      this.showEdgeItems = true;
    }

    // Chiudi solo i pannelli senza voci attive
    if (!hasActiveTenant) {
      this.showTenantItems = false;
    }
    if (!hasActiveEquipment) {
      this.showEquipmentItems = false;
    }
    if (!hasActiveUser) {
      this.showUserItems = false;
    }
    if (!hasActiveDashboard) {
      this.showDashboardItems = false;
    }
    if (!hasActiveEdge) {
      this.showEdgeItems = false;
    }

  }

  toggleDrawer(section: string) {
    const currentUrl = this.router.url;
    let isActiveSection = false;

    // Verifica se la sezione è attiva
    switch (section) {
      case 'tenant':
        isActiveSection = currentUrl.includes('/search-tenant') || currentUrl.includes('/add-tenant');
        if (!isActiveSection) {
          this.showTenantItems = !this.showTenantItems;
        }
        break;
      case 'equipment':
        isActiveSection = currentUrl.includes('/search-equipment') || currentUrl.includes('/add-equipment');
        if (!isActiveSection) {
          this.showEquipmentItems = !this.showEquipmentItems;
        }
        break;
      case 'user':
        isActiveSection = currentUrl.includes('/search-users') || currentUrl.includes('/add-users');
        if (!isActiveSection) {
          this.showUserItems = !this.showUserItems;
        }
        break;
      case 'dashboard':
        isActiveSection = currentUrl.includes('/userdashboard-list') || currentUrl.includes('/userdashboard-new');
        if (!isActiveSection) {
          this.showDashboardItems = !this.showDashboardItems;
        }
        break;
        case 'edge':
        isActiveSection = currentUrl.includes('/edge-list') || currentUrl.includes('/edge-detail');
        if (!isActiveSection) {
          this.showEdgeItems = !this.showEdgeItems;
        }
        break;
    }
  }

  public open(modal: any): void {
    this.modalService.open(modal);
  }

  public hasRole(role: string): boolean {
    return this.userService.hasRole(role);
  }

  isPliotAdmin(): boolean {
    return this.userGroupNames.includes('pliot_admin');
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
          case 'edge':
            this.showEdgeItems = true ;
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



  logout() {
    this.userService.keycloak.logout({
      redirectUri: window.location.origin
    });
  }
}
