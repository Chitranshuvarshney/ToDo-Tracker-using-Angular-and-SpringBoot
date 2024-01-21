import { Component } from '@angular/core';
import { LoginService } from '../services/login.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css'],
})
export class ContactComponent {
  constructor(
    private snackBar: MatSnackBar,
    private userService: UserService
  ) {}

  credentials = {
    name: '',
    email: '',
    message: '',
  };

  OnSubmit(contactForm: any) {
    if (
      this.credentials.name &&
      this.credentials.email &&
      this.credentials.message
    ) {
      this.userService.postMail(this.credentials).subscribe({
        next: () => {
          contactForm.resetForm();
          this.snackBar.open('Form Submitted', 'success', {
            horizontalPosition: 'center',
            verticalPosition: 'top',
            duration: 5000,
          });
        },
      });
      console.log('we have to submit the form to server');
    } else {
      console.log('Fields should not be empty');
    }
  }
}
