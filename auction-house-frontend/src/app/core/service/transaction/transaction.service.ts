import {Injectable} from '@angular/core';
import {ProductModel} from "../../../shared/models/product.model";
import {BidModel} from "../../../shared/models/bid.model";
import {TransactionModel} from "../../../shared/models/transaction.model";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {getLoggedEmail} from "../../guard/authorization.guard";

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  constructor(private http: HttpClient) {
  }

  saveTransaction(product: ProductModel, latestBid: BidModel): Observable<TransactionModel> {
    const transaction: TransactionModel = {
      productId: product.id,
      buyerEmail: latestBid.bidderEmail,
      sellerEmail: product.sellerEmail,
      amount: latestBid.bidAmount,
      transactionTime: new Date().toISOString()
    };

    return this.http.post<TransactionModel>('transaction/v1/save', transaction);
  }

  getTransactionsBySeller(): Observable<TransactionModel[]> {
    let email = getLoggedEmail();
    return this.http.get<TransactionModel[]>(`transaction/v1/by-seller/${email}`);
  }

  getTransactionsByBuyer(): Observable<TransactionModel[]> {
    let email = getLoggedEmail();
    return this.http.get<TransactionModel[]>(`transaction/v1/by-buyer/${email}`);
  }
}
