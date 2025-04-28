import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from './service/user.service';

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

}
