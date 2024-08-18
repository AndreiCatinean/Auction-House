import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ProductModel} from "../../../shared/models/product.model";
import {Observable} from "rxjs";
import {getLoggedEmail} from "../../guard/authorization.guard";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) {
  }

  getById(id: string | undefined): Observable<ProductModel> {
    return this.http.get<ProductModel>(`product/v1/${id}`);
  }

  getAll(): Observable<ProductModel[]> {
    return this.http.get<ProductModel[]>('product/v1/all');
  }


  getUserProducts(): Observable<ProductModel[]> {

    let seller_email = getLoggedEmail();
    console.error(seller_email);
    return this.http.get<ProductModel[]>(`product/v1/seller/${seller_email}`);
  }

  getClosingOffers(): Observable<ProductModel[]> {
    return this.http.get<ProductModel[]>('product/v1/closing-offers');
  }

  saveProduct(product: ProductModel): Observable<any> {
    product.sellerEmail = getLoggedEmail();
    return this.http.post('product/v1/save', product);
  }

  setStatus(productId: string): Observable<ProductModel[]> {
    return this.http.put<ProductModel[]>(`product/v1/close-status/${productId}`, null);
  }
}
