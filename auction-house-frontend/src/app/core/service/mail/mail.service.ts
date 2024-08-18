import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MailService {

  private readonly appMail: string = "auction-house@mail-domain.com";

  constructor(private http: HttpClient) {
  }

  sendBuyer(to: string, title: string): Observable<any> {
    const mailRequest = {
      from: this.appMail,
      to: to,
      subject: "Product Purchase Confirmation",
      body: title
    };

    return this.http.post(`mail/v1/sync`, mailRequest);
  }

  sendSeller(to: string, title: string): Observable<any> {
    const mailRequest = {
      from: this.appMail,
      to: to,
      subject: "Product Sold Notification",
      body: title
    };

    return this.http.post(`mail/v1/sync`, mailRequest);
  }

  notSold(to: string, title: string): Observable<any> {
    const mailRequest = {
      from: this.appMail,
      to: to,
      subject: "Product Not Sold Notification",
      body: title
    };

    return this.http.post(`mail/v1/sync`, mailRequest);
  }


}
