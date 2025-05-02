import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from '../../service/user.service';
import { ConfigurationService } from '../../service/config.service.';


@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  userFirstName: string | null = null;
  constructor(private modalService: NgbModal,
              private userService: UserService,
              private configuration: ConfigurationService ) {
    this.userFirstName = this.userService.getCurrentFirstName(); // o getUsername() se preferisci
    console.info("Utente loggato:", this.userFirstName);
    if ( configuration.config.mode== "EDGE" )
        this.userService.federateCurrentUser();
    
  }
}
