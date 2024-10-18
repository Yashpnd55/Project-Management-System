import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthServiceTsService } from '../../services/auth.service.ts.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  loginForm = this.fb.group({
    username: ['', [Validators.required]],
    password: ['', Validators.required]
  })
  
  constructor(private fb: FormBuilder,
    private authService: AuthServiceTsService,
    private messageService: MessageService,
  private router: Router
  ) {}

  get username() {
    return this.loginForm.controls['username'];
  }

  get password() {
    return this.loginForm.controls['password'];
  }

  login() {

  }

  loginUser() {
    const {username, password} = this.loginForm.value;
    const dto = { 
      username: username || '', 
      password: password || '' 
    };    this.authService.loginService(dto).subscribe(
      response => {
        if(response.length > 0) {
          sessionStorage.setItem('username', username as string);
          this.router.navigate(['/home']);
        }
        else {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Username or Password is wrong' });
        }
      },
      error => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Something went wrong' });
      }
    )
  }
}
