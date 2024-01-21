import { Component, OnInit, inject } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { AsyncPipe, NgIf } from '@angular/common';
import { LoginService } from '../services/login.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  public loggedIn = false;

  constructor(
    private loginService: LoginService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loggedIn = this.loginService.isLoggedIn();
  }

  logoutUser() {
    const confirmed = window.confirm('Are you sure you want to leave?');
    if (confirmed) {
      this.loginService.logout();
      location.reload();
      this.snackBar.open('Logout Successfully', 'success', {
        horizontalPosition: 'center',
        verticalPosition: 'top',
        duration: 5000,
      });
    }
  }

  private breakpointObserver = inject(BreakpointObserver);

  isHandset$: Observable<boolean> = this.breakpointObserver
    .observe(Breakpoints.Handset)
    .pipe(
      map((result) => result.matches),
      shareReplay()
    );
}
