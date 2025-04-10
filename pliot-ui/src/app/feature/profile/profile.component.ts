import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-profile',
  standalone: false,
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  idpId: string | null = '';
  username: string | null = '';
  firstName: string | null = '';
  lastName: string | null = '';
  email: string | null = '';
  userId: string | null = '';

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.username = this.userService.getCurrentUserName();
    this.firstName = this.userService.getCurrentFirstName();
    this.lastName = this.userService.getCurrentLastName();
    this.email = this.userService.getCurrentUserEDmail();
    this.userId = this.userService.getCurrentUserId();
    this.idpId = this.userService.getCurrentUserId();

  }
  onSubmit(): void {
      const updatedUser = {
        username: this.username,
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.email,
        idpId: this.idpId,
        userId: this.userId
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
}
