import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog } from '@angular/material/dialog';
import { ProductsComponent } from './products.component';
import { ProductService } from '../../../core/service/product/product.service';
import { BidService } from '../../../core/service/bid/bid.service';
import { FavoriteOfferService } from '../../../core/service/favoriteOffer/favorite-offer.service';
import { of } from 'rxjs';
import { ProductModel } from '../../../shared/models/product.model';
import { BidModel } from '../../../shared/models/bid.model';
import { RouterModule } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import { MatDialogRef } from '@angular/material/dialog';
import {NavbarComponent} from "../../navbar/navbar.component";
import {AddProductDialogComponent} from "./add-product-dialog/add-product-dialog.component";

describe('ProductsComponent', () => {
  let component: ProductsComponent;
  let fixture: ComponentFixture<ProductsComponent>;
  let productService: jasmine.SpyObj<ProductService>;
  let bidService: jasmine.SpyObj<BidService>;
  let favoriteOfferService: jasmine.SpyObj<FavoriteOfferService>;
  let dialog: jasmine.SpyObj<MatDialog>;

  beforeEach(async () => {
    const productServiceSpy = jasmine.createSpyObj<ProductService>('ProductService', ['getAll', 'getUserProducts', 'getById', 'getClosingOffers', 'saveProduct']);
    const bidServiceSpy = jasmine.createSpyObj<BidService>('BidService', ['getLatestByProductId']);
    const favoriteOfferServiceSpy = jasmine.createSpyObj<FavoriteOfferService>('FavoriteOfferService', ['getFavoriteOffers']);
    const dialogSpy = jasmine.createSpyObj<MatDialog>('MatDialog', ['open']);
    const matDialogRefSpy = jasmine.createSpyObj<MatDialogRef<ProductsComponent>>('MatDialogRef', ['afterClosed']);

    await TestBed.configureTestingModule({
      imports: [HttpClientModule, CommonModule, FormsModule, RouterModule, NavbarComponent],
      declarations: [ProductsComponent],
      providers: [
        { provide: ProductService, useValue: productServiceSpy },
        { provide: BidService, useValue: bidServiceSpy },
        { provide: FavoriteOfferService, useValue: favoriteOfferServiceSpy },
        { provide: MatDialog, useValue: dialogSpy },
        { provide: MatDialogRef, useValue: matDialogRefSpy }
      ]
    }).compileComponents();

    productService = TestBed.inject(ProductService) as jasmine.SpyObj<ProductService>;
    bidService = TestBed.inject(BidService) as jasmine.SpyObj<BidService>;
    favoriteOfferService = TestBed.inject(FavoriteOfferService) as jasmine.SpyObj<FavoriteOfferService>;
    dialog = TestBed.inject(MatDialog) as jasmine.SpyObj<MatDialog>;

    fixture = TestBed.createComponent(ProductsComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get all products on init for "/dashboard/products"', () => {
    // given
    component['endpoint'] = '/dashboard/products';
    const products: ProductModel[] = [{ id: '1', sellerEmail: 'seller@example.com', title: 'Product 1', description: 'Description 1', startingPrice: 10, imageUrl: 'image1.jpg', categoryId: 1 }];
    const bids: BidModel[] = [{ id: '1', productId: '1', bidderEmail: 'bidder@example.com', bidAmount: 15 }];
    productService.getAll.and.returnValue(of(products));
    bidService.getLatestByProductId.and.returnValue(of(bids[0]));

    // when
    component.ngOnInit();

    // then
    expect(productService.getAll).toHaveBeenCalled();
    expect(bidService.getLatestByProductId).toHaveBeenCalled();
    expect(component.products.size).toEqual(1);
    expect(component.products.get(products[0])).toEqual(bids[0]);
  });

  it('should get user products on init for "/dashboard/listed-products"', () => {
    // given
    component['endpoint'] = '/dashboard/listed-products';
    const products: ProductModel[] = [{ id: '1', sellerEmail: 'seller@example.com', title: 'Product 1', description: 'Description 1', startingPrice: 10, imageUrl: 'image1.jpg', categoryId: 1 }];
    const bids: BidModel[] = [{ id: '1', productId: '1', bidderEmail: 'bidder@example.com', bidAmount: 15 }];
    productService.getUserProducts.and.returnValue(of(products));
    bidService.getLatestByProductId.and.returnValue(of(bids[0]));

    // when
    component.ngOnInit();

    // then
    expect(productService.getUserProducts).toHaveBeenCalled();
    expect(bidService.getLatestByProductId).toHaveBeenCalledTimes(products.length);
    expect(component.products.size).toEqual(products.length);
  });

  it('should get user products on init for "/dashboard/listed-products"', () => {
    // given
    component['endpoint'] = '/dashboard/listed-products';
    const products: ProductModel[] = [
      { id: '1', sellerEmail: 'seller@example.com', title: 'Product 1', description: 'Description 1', startingPrice: 10, imageUrl: 'image1.jpg', categoryId: 1 }
    ];
    const bids: BidModel[] = [{ id: '1', productId: '1', bidderEmail: 'bidder@example.com', bidAmount: 15 }];
    productService.getUserProducts.and.returnValue(of(products));
    bids.forEach(bid => bidService.getLatestByProductId.withArgs(bid.productId).and.returnValue(of(bid)));

    // when
    component.ngOnInit();

    // then
    expect(productService.getUserProducts).toHaveBeenCalled();
    products.forEach(product => {
      expect(bidService.getLatestByProductId).toHaveBeenCalledWith(product.id);
    });
    expect(component.products.size).toEqual(products.length);
  });

  it('should get favorite offers on init for "/dashboard/fav-offers"', () => {
    // given
    component['endpoint'] = '/dashboard/fav-offers';
    const favoriteOffers: { userEmail: string, productId: string }[] = [{ userEmail: 'user@example.com', productId: '1' }];
    const products: ProductModel[] = [{ id: '1', sellerEmail: 'seller@example.com', title: 'Product 1', description: 'Description 1', startingPrice: 10, imageUrl: 'image1.jpg', categoryId: 1 }];
    const bids: BidModel[] = [{ id: '1', productId: '1', bidderEmail: 'bidder@example.com', bidAmount: 15 }];
    // @ts-ignore
    productService.getById.and.callFake(productId => of(products.find(product => product.id === productId)));
    bids.forEach(bid => bidService.getLatestByProductId.withArgs(bid.productId).and.returnValue(of(bid)));
    localStorage.setItem('favoriteOffers', JSON.stringify(favoriteOffers));

    // when
    component.ngOnInit();

    // then
    expect(productService.getById).toHaveBeenCalledTimes(favoriteOffers.length);
    favoriteOffers.forEach(offer => {
      expect(bidService.getLatestByProductId).toHaveBeenCalledWith(offer.productId);
    });
    expect(component.products.size).toEqual(favoriteOffers.length);
  });

  it('should get closing offers on init for "/dashboard/close-offers"', () => {
    // given
    component['endpoint'] = '/dashboard/close-offers';
    const products: ProductModel[] = [
      { id: '1', sellerEmail: 'seller@example.com', title: 'Product 1', description: 'Description 1', startingPrice: 10, imageUrl: 'image1.jpg', categoryId: 1 }
    ];
    const bids: BidModel[] = [{ id: '1', productId: '1', bidderEmail: 'bidder@example.com', bidAmount: 15 }];
    productService.getClosingOffers.and.returnValue(of(products));
    bids.forEach(bid => bidService.getLatestByProductId.withArgs(bid.productId).and.returnValue(of(bid)));

    // when
    component.ngOnInit();

    // then
    expect(productService.getClosingOffers).toHaveBeenCalled();
    products.forEach(product => {
      expect(bidService.getLatestByProductId).toHaveBeenCalledWith(product.id);
    });
    expect(component.products.size).toEqual(products.length);
  });

  it('should open add product dialog', () => {
    // given
    const dialogRefSpyObj = jasmine.createSpyObj({ afterClosed: of(null) });
    const matDialogRefSpy = jasmine.createSpyObj('MatDialogRef', ['afterClosed']);
    matDialogRefSpy.afterClosed.and.returnValue(of({}));

    dialog.open.and.returnValue(matDialogRefSpy);

    // when
    component.openAddProductDialog();

    // then
    expect(dialog.open).toHaveBeenCalledWith(AddProductDialogComponent, { width: '400px', data: {} });
  });
});
