import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class SignupService {
  url = 'http://localhost:9000/api/v3/register';

  constructor(private http: HttpClient) {}

  register(credentials: any) {
    console.log(credentials);

    return this.http.post(`${this.url}`, credentials, {
      observe: 'response',
    });
  }
}
