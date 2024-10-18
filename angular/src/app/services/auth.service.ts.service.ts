import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../interfaces/User';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceTsService {

  private headers: HttpHeaders;

  constructor(private http: HttpClient) {
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
   }

  loginService(loginDto: {username: string, password: string}): Observable<any> {
    let apiUrl = 'http://localhost:9101/pms/login';
    return this.http.post(apiUrl, loginDto, {responseType: 'text'});
  }

  registerUser(userDetails: User) {
    const registerDto = {
      username: userDetails.fullName,
      password: userDetails.password
    };
    let apiUrl = 'http://localhost:9101/pms/register';
    return this.http.post(apiUrl, registerDto, {headers: this.headers, responseType: 'text'});
  }

  getUserByUsername(username: string): Observable<User[]>{
    let apiUrl = 'http://localhost:9101/pms/login';
    return this.http.get<User[]>(apiUrl);
  }
}
