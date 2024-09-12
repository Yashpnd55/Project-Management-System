import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginData = {
    username: '',
    password: ''
  };

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit() {
    this.http.post('http://localhost:8080/api/login', this.loginData)
      .subscribe({
        next: (response) => {
          console.log('User logged in successfully');
          this.router.navigate(['/home']); // Redirect after login
        },
        error: (err) => {
          console.error('Login failed', err);
        }
      });
  }
}
