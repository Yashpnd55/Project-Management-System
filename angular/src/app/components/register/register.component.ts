import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { passwordMatchValidator } from '../../shared/password-match.directive';
import { AuthServiceTsService } from '../../services/auth.service.ts.service';
import { User } from '../../interfaces/User';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  registerForm = this.fb.group({
    fullName: ['', [Validators.required, Validators.pattern(/^[a-zA-Z]+(?: [a-zA-Z]+)*$/)]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
    confirmPassword: ['', Validators.required]
  }, {
    validators: passwordMatchValidator
  })
 
  constructor(private fb: FormBuilder, 
    private authService: AuthServiceTsService,
    private messageService: MessageService,
  private router: Router ) {}

  get fullName() {
    return this.registerForm.controls['fullName'];
  }

  get email() {
    return this.registerForm.controls['email'];
  }

  get password() {
    return this.registerForm.controls['password'];
  }

  get confirmPassword() {
    return this.registerForm.controls['confirmPassword'];
  }

  submitDetails() {
    console.log(this.registerForm.value);
    const postData = { ...this.registerForm.value};
    delete postData.confirmPassword;
    this.authService.registerUser(postData  as User).subscribe(
      response => {
        console.log(response);
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Registered Successfully' });
        this.router.navigate(['login']);
      }, 
      error => {console.log(error);
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Something went wrong' });
  })
  }

}
