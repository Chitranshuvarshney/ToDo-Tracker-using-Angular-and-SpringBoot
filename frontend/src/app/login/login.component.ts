import { Component } from '@angular/core';
import { LoginService } from '../services/login.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  constructor(
    private loginService: LoginService,
    private snackBar: MatSnackBar
  ) {}

  credentials = {
    email: '',
    password: '',
  };

  hide = true;

  OnSubmit(loginForm: any) {
    if (this.credentials.email && this.credentials.password) {
      console.log('we have to submit the form to server');
      // token generate
      this.loginService.generateToken(this.credentials).subscribe(
        (response: any) => {
          console.log(response);
          this.snackBar.open('Login Successfully', 'success', {
            horizontalPosition: 'center',
            verticalPosition: 'top',
            duration: 5000,
          });
          this.loginService.loginUser(response);
          window.location.href = '/dashboard';
        },
        (error) => {
          console.log(error);
          alert(
            "The gmail or password you entered is incorrect ! \nIf you don't have an account, first sign up by clicking below link then you will be access the dashboard !"
          );
        }
      );
    } else {
      console.log('Fields should not be empty');
    }
  }
}
