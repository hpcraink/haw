import { Component } from '@angular/core';
import { AuthService } from '../+auth/auth.service';

@Component({
  selector: 'app-statistitcs',
  templateUrl: './statistics.component.html'
})

export class StatisticsComponent {
  constructor (private authService: AuthService) {}

  isLoggedIn() {
    return this.authService.isLoggedIn();
  }
}
