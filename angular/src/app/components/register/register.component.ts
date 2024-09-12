import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registrationData = {
    username: '',
    email: '',
    password: ''
  };

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit() {
    this.http.post('http://localhost:8080/api/register', this.registrationData)
      .subscribe({
        next: (response) => {
          console.log('User registered successfully');
          this.router.navigate(['/login']); // Redirect to login after successful registration
        },
        error: (err) => {
          console.error('Registration failed', err);
        }
      });
  }
}
