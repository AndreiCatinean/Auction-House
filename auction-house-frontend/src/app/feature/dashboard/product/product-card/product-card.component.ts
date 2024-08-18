import {Component, DestroyRef, Input} from '@angular/core';
import {ProductModel} from "../../../../shared/models/product.model";
import {Router} from "@angular/router";
import {BidModel} from "../../../../shared/models/bid.model";
import {ProductService} from "../../../../core/service/product/product.service";
import {TransactionService} from "../../../../core/service/transaction/transaction.service";
import {MailService} from "../../../../core/service/mail/mail.service";

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrl: './product-card.component.css'
})
export class ProductCardComponent {

  @Input() product!: ProductModel;
  @Input() latestBid!: BidModel | null;
  @Input() isClosingOffersPage: boolean = false;


  constructor(private router: Router,
              private productService: ProductService,
              private mailService: MailService,
              private destroyRef: DestroyRef,
              private transactionService: TransactionService) {
  }

  viewDetails(id: string | undefined): void {
    this.router.navigate(['/dashboard/product/' + id]);
  }

  calculateRemainingTime(endTime: string): string {
    const currentTime = new Date().getTime();
    const endTimeInMilliseconds = new Date(endTime).getTime();
    let timeDifference = Math.max(0, endTimeInMilliseconds - currentTime) / 1000; // in seconds

    const days = Math.floor(timeDifference / (3600 * 24));
    timeDifference -= days * 3600 * 24;

    const hours = Math.floor(timeDifference / 3600);
    timeDifference -= hours * 3600;

    const minutes = Math.floor(timeDifference / 60);


    return `${days} days, ${hours} hours, ${minutes} minutes`;
  }

  getCardColor(): string {
    if (this.product.status === 'inactive') {
      return 'bg-success';
    } else if (this.product.endTime && new Date(this.product.endTime) < new Date()) {
      return 'bg-danger';
    } else {
      return '';
    }
  }

  endAuction(): void {
    if (!this.product.id) {
      console.error('Product ID is undefined');
      return;
    }

    this.productService.setStatus(this.product.id).subscribe(response => {
    });

    if (this.latestBid && this.product) {
      this.transactionService.saveTransaction(this.product, this.latestBid).subscribe(response => {
      });

      this.mailService.sendBuyer(this.latestBid.bidderEmail, this.product.title).subscribe(response => {
        console.log('Buyer email sent:', response);
      });

      this.mailService.sendSeller(this.product.sellerEmail, this.product.title).subscribe(response => {
      });

    } else {
      this.mailService.notSold(this.product.sellerEmail,this.product.title).subscribe(response => {
      });
      console.error('Latest bid or product is undefined');
    }
    location.reload();
  }
}
