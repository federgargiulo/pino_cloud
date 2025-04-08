import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-profile',
  standalone: false,
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
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
  }
}
