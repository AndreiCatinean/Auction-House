import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

import {getLoggedEmail} from '../../guard/authorization.guard'
import {FavoriteOfferModel} from "../../../shared/models/favoriteOffer.model";

@Injectable({
  providedIn: 'root'
})
export class FavoriteOfferService {

  constructor(private http: HttpClient) {
  }

  addToFavorites(productId: string | undefined): Observable<FavoriteOfferModel> {
    const userEmail = getLoggedEmail();
    return this.http.post<FavoriteOfferModel>('favorite-offers/save', {userEmail, productId});
  }

  getFavoriteOffers(): Observable<FavoriteOfferModel[]> {
    const userEmail = getLoggedEmail();
    return this.http.get<FavoriteOfferModel[]>(`favorite-offers/user/${userEmail}`);
  }

  removeFromFavorites(productId: string | undefined): Observable<any> {
    const userEmail = getLoggedEmail();
    return this.http.delete(`favorite-offers/delete?productId=${productId}&email=${userEmail}`);
  }
}
