import { Component } from '@angular/core';
import { SignupService } from '../services/signup.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent {
  constructor(
    private signupService: SignupService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  credentials = {
    email: '',
    username: '',
    password: '',
  };

  hide = true;

  OnSubmit(signupForm: any) {
    if (
      this.credentials.email &&
      this.credentials.username &&
      this.credentials.password
    ) {
      console.log('we have to submit the form to server');
      this.signupService.register(this.credentials).subscribe(
        (response) => {
          console.log(response);
          this.snackBar.open('Register Successfully', 'success', {
            horizontalPosition: 'center',
            verticalPosition: 'top',
            duration: 5000,
          });
          this.router.navigate(['/login']);
        },
        (error) => {
          alert('User is already register with this emailId!');
        }
      );
    } else {
      console.log('Fields should not be empty');
    }
  }
}
