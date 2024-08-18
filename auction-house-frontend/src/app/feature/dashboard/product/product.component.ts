import {ProductModel} from "../../../shared/models/product.model";
import {ProductService} from "../../../core/service/product/product.service";


import {Component, DestroyRef, OnInit} from '@angular/core';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
import {ActivatedRoute} from '@angular/router';
import {AddBidModalComponent} from "../bid/add-bid-modal/add-bid-modal.component";
import {MatDialog} from "@angular/material/dialog";
import {FavoriteOfferService} from "../../../core/service/favoriteOffer/favorite-offer.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrl: './product.component.scss'
})
export class ProductComponent implements OnInit {

  product?: ProductModel;
  id?: string;
  isFavorite: boolean = false;


  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private destroyRef: DestroyRef,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private favoritesService: FavoriteOfferService,
  ) {
  }

  ngOnInit(): void {
    this.getProductbyId();
    this.checkIfFavorite();
  }

  private getProductbyId(): void {
    this.route.params
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(response => {
        this.id = response['id'];

        this.productService.getById(this.id || '')
          .pipe(takeUntilDestroyed(this.destroyRef))
          .subscribe(response => this.product = response);
      });
  }

  addToFavorites(): void {
    if (this.product) {

      const userData = JSON.parse(localStorage.getItem('loggedUser') || '{}');
      const userEmail = userData.email;

      this.favoritesService.addToFavorites(this.product.id)
        .subscribe(() => {
        }, error => {
          console.error('Error while adding to favorites:', error);
        });
      const favoriteOffers = JSON.parse(localStorage.getItem('favoriteOffers') || '[]');
      favoriteOffers.push({userEmail: userEmail, productId: this.product.id});
      localStorage.setItem('favoriteOffers', JSON.stringify(favoriteOffers));
      console.log('Added to favorites:', this.product.id);
      this.isFavorite = true;

    }
  }

  checkIfFavorite(): void {
    const favoriteOffers = JSON.parse(localStorage.getItem('favoriteOffers') || '[]');
    this.isFavorite = favoriteOffers.some((offer: any) => offer.productId === this.id);
  }

  removeFromFavorites(): void {
    if (!this.product || !this.id) return;

    const favoriteOffers = JSON.parse(localStorage.getItem('favoriteOffers') || '[]');
    const updatedFavorites = favoriteOffers.filter((offer: any) => offer.productId !== this.id);
    localStorage.setItem('favoriteOffers', JSON.stringify(updatedFavorites));

    this.favoritesService.removeFromFavorites(this.product.id).subscribe(() => {
      this.isFavorite = false;
      this.snackBar.open('Removed from favorites', 'Close', {duration: 2000});

    }, (error: any) => {
      console.error('Error while removing from favorites:', error);
      this.snackBar.open('Failed to remove from favorites', 'Close', {duration: 2000});
    });
  }

  openAddBidModal(): void {
    const dialogRef = this.dialog.open(AddBidModalComponent, {
      width: '400px',
      data: {productId: this.id}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result && result.success) {
        location.reload();
        this.snackBar.open('Bid placed successfully', 'Close', {duration: 2000});
      } else if (result && !result.success) {
        this.snackBar.open(`Error placing bid: ${result.error}`, 'Close', {duration: 5000});
      }
    });
  }
}
