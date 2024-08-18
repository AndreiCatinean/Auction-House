import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BidModel} from "../../../shared/models/bid.model";
import {Observable} from "rxjs";
import {getLoggedEmail} from "../../guard/authorization.guard";


@Injectable({
  providedIn: 'root'
})
export class BidService {

  constructor(private http: HttpClient) {
  }

  getLatestByProductId(id: string | undefined): Observable<BidModel> {
    return this.http.get<BidModel>(`bid/v1/latest-by-product/${id}`);
  }

  addBid(bid: BidModel): Observable<any> {
    bid.bidderEmail = getLoggedEmail();
    return this.http.post('bid/v1/save', bid);
  }
}
