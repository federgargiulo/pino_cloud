import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.scss'
})


export class AppComponent {
  showTenantItems = false;
  showEquipmentItems = false;
  showUsersItems = false;
  showDashboardItems = false;
  title = 'pliot-ui';
  constructor(private modalService: NgbModal) {
  }

  public open(modal: any): void {
    this.modalService.open(modal);
  }
}
