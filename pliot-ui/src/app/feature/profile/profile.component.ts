
import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';


@Component({
  selector: 'app-profile',
  standalone: false,
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  idpId: string | null = '';
  username: string | null = '';
  firstName: string | null = '';
  lastName: string | null = '';
  email: string | null = '';
  userId: string | null = '';
  issuer: string | null = '';
  userGroups: string[] = [];
  tenant: string | null = '';

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.username = this.userService.getCurrentUserName();
    this.firstName = this.userService.getCurrentFirstName();
    this.lastName = this.userService.getCurrentLastName();
    this.email = this.userService.getCurrentUserEDmail();
    this.userId = this.userService.getCurrentUserId();
    this.tenant = this.userService.getCurrentUserTenant();
    this.idpId = this.userService.getCurrentUserId();
    this.issuer = this.userService.getGetJWTAttribute('iss'); // <--- ottieni "iss"
    console.log('Issuer:', this.issuer);
    console.log('userId:', this.userId);
    console.log('username:', this.username);

      if (this.userId) {
        this.userService.getUserById(this.userId).subscribe({
          next: (data: any) => {
            if (data && data.body) {
              const user = data.body;
              this.firstName = user.firstName;
              this.lastName = user.lastName;
              this.email = user.email;
              this.username = user.username || this.username;
              this.userGroups = user.usrGrp?.map((g: any) => g.description || g.grpName) || [];
              this.tenant = user.tenant;
            }
          },
          error: (err) => {
            console.error('Errore nel caricamento del profilo utente:', err);
          }
        });
      }
    }



  onSubmit(): void {
      const updatedUser = {
        username: this.username,
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.email,
        idpId: this.idpId,
        userId: this.userId,
        tenant: this.tenant
      };

      this.userService.updateUser(updatedUser).subscribe({
        next: () => {
          alert('Profilo aggiornato con successo!');
        },
        error: (err) => {
          console.error('Errore durante l\'aggiornamento:', err);
          alert('Errore durante l\'aggiornamento del profilo.');
        }
      });
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
