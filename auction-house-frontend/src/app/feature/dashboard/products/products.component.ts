import {Component, DestroyRef, OnInit} from '@angular/core';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
import {Router} from '@angular/router';
import {ProductService} from "../../../core/service/product/product.service";
import {ProductModel} from "../../../shared/models/product.model";
import {MatDialog} from "@angular/material/dialog";
import {AddProductDialogComponent} from "./add-product-dialog/add-product-dialog.component";
import {BidModel} from "../../../shared/models/bid.model";
import {BidService} from "../../../core/service/bid/bid.service";
import {FavoriteOfferService} from "../../../core/service/favoriteOffer/favorite-offer.service";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrl: './products.component.scss'
})
export class ProductsComponent implements OnInit {

  products: Map<ProductModel, BidModel | null> = new Map<ProductModel, BidModel | null>();
  protected  endpoint: string;


  constructor(
    private productService: ProductService,
    private bidService: BidService,
    private favoriteOfferService: FavoriteOfferService,
    private router: Router,
    private destroyRef: DestroyRef,
    public dialog: MatDialog
  ) {
    this.endpoint = this.router.url;
  }

  ngOnInit(): void {
    if (this.endpoint === '/dashboard/products') {
      this.getProducts();
    } else if (this.endpoint === '/dashboard/listed-products') {
      this.getUserProducts();
    } else if (this.endpoint === '/dashboard/fav-offers') {
      this.getFavoriteOffers();
    } else if (this.endpoint === '/dashboard/close-offers') {
      this.getClosingOffers();
    }

  }

  private getProducts(): void {
    this.productService.getAll()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (products: ProductModel[]) => {
          products.forEach((product: ProductModel) => {
            this.bidService.getLatestByProductId(product.id)
              .pipe(takeUntilDestroyed(this.destroyRef))
              .subscribe({
                next: (latestBid: BidModel) => {
                  this.products.set(product, latestBid);
                },
                error: () => {
                  this.products.set(product, null);
                }
              });
          });
        },
        error: err => console.log(err)
      });
  }

  private getUserProducts(): void {
    this.productService.getUserProducts()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (products: ProductModel[]) => {
          products.forEach((product: ProductModel) => {
            this.bidService.getLatestByProductId(product.id)
              .pipe(takeUntilDestroyed(this.destroyRef))
              .subscribe({
                next: (latestBid: BidModel) => {
                  this.products.set(product, latestBid);
                },
                error: () => {
                  this.products.set(product, null);
                }
              });
          });
        },
        error: err => console.log(err)
      });
  }

  private getFavoriteOffers(): void {
    const favoriteOffers: {
      userEmail: string,
      productId: string
    }[] = JSON.parse(localStorage.getItem('favoriteOffers') || '[]');


    const favoriteProductIds: string[] = favoriteOffers.map(offer => offer.productId);

    console.error(favoriteProductIds);
    favoriteProductIds.forEach(productId => {

      this.productService.getById(productId)
        .pipe(takeUntilDestroyed(this.destroyRef))
        .subscribe({
          next: (product: ProductModel) => {
            this.products.set(product, null);
            this.bidService.getLatestByProductId(product.id)
              .pipe(takeUntilDestroyed(this.destroyRef))
              .subscribe({
                next: (latestBid: BidModel) => {
                  this.products.set(product, latestBid);
                },
                error: () => {
                  this.products.set(product, null);
                }
              });
          },
          error: () => {

          }
        });
    });
  }


  private getClosingOffers(): void {
    this.productService.getClosingOffers()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (products: ProductModel[]) => {
          products.filter(product => product.status !== 'inactive')
            .forEach((product: ProductModel) => {
              this.bidService.getLatestByProductId(product.id)
                .pipe(takeUntilDestroyed(this.destroyRef))
                .subscribe({
                  next: (latestBid: BidModel) => {
                    this.products.set(product, latestBid);
                  },
                  error: () => {

                    this.products.set(product, null);
                  }
                });
            });
        },
        error: err => console.error('Error while fetching closing offers:', err)
      });
  }


  openAddProductDialog(): void {
    const dialogRef = this.dialog.open(AddProductDialogComponent, {
      width: '400px',
      data: {}
    });

    dialogRef.afterClosed().subscribe((newProduct: ProductModel) => {
      if (newProduct) {
        location.reload();
        console.log('New product added:', newProduct);
      }
    });
  }
}
