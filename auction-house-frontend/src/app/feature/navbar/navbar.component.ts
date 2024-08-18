import {Component, DestroyRef} from '@angular/core';
import {Router} from '@angular/router';
import {NgIf} from "@angular/common";
import {MatIconButton} from "@angular/material/button";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    NgIf,
    MatIconButton,
    MatMenuTrigger,
    MatMenu,
    MatMenuItem
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {

  isAdmin: boolean = false;

  constructor(
    private router: Router,
    private destroyRef: DestroyRef,
  ) {
  }


  ngOnInit(): void {
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser') || '');
    this.isAdmin = loggedUser.role === 'ADMIN';
  }

  navigateToDashboard(): void {
    this.router.navigateByUrl('/dashboard/products');
  }

  logOut(): void {
    this.clearCookies();
    localStorage.removeItem('loggedUser');
    localStorage.removeItem('favoriteOffers');
    this.router.navigateByUrl('/auth/login');
  }

  private clearCookies(): void {
    const cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
      const cookie = cookies[i];
      const equalPos = cookie.indexOf('=');
      const name = equalPos > -1 ? cookie.slice(0, equalPos) : cookie;
      document.cookie = name + '=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/;';
    }
  }

  navigateToListedProducts() {
    this.router.navigateByUrl('/dashboard/listed-products');

  }

  navigateToMyTransactions() {
    this.router.navigateByUrl('/dashboard/my-transactions');

  }

  navigateToFavOffers() {
    this.router.navigateByUrl('/dashboard/fav-offers');

  }

  navigateToCloseOffers(): void {
    this.router.navigateByUrl('/dashboard/close-offers');
  }
}
