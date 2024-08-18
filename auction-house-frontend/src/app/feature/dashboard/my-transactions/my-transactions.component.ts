import {Component, DestroyRef} from '@angular/core';
import {getLoggedEmail} from "../../../core/guard/authorization.guard";
import {ActivatedRoute} from "@angular/router";
import {ProductService} from "../../../core/service/product/product.service";
import {TransactionService} from "../../../core/service/transaction/transaction.service";
import {TransactionModel} from "../../../shared/models/transaction.model";
import {TransactionInformationModel} from "../../../shared/models/transactionInformation.model";

@Component({
  selector: 'app-my-transactions',
  templateUrl: './my-transactions.component.html',
  styleUrl: './my-transactions.component.scss'
})
export class MyTransactionsComponent {
  sellerTransactions: TransactionInformationModel[] = [];
  buyerTransactions: TransactionInformationModel[] = [];


  constructor(
    private route: ActivatedRoute,
    private transactionService: TransactionService,
    private productService: ProductService,
    private destroyRef: DestroyRef,
  ) {
  }

  ngOnInit(): void {
    this.fetchTransactions();
  }

  private fetchTransactions(): void {

    const userEmail = getLoggedEmail();

    this.transactionService.getTransactionsBySeller()
      .subscribe(transactions => {
        this.fetchProductInfo(transactions, this.sellerTransactions);
      });

    this.transactionService.getTransactionsByBuyer()
      .subscribe(transactions => {
        this.fetchProductInfo(transactions, this.buyerTransactions);
      });
  }

  private fetchProductInfo(transactions: TransactionModel[], targetArray: TransactionInformationModel[]): void {
    for (const transaction of transactions) {
      this.productService.getById(transaction.productId)
        .subscribe(product => {
          targetArray.push({transaction, product});
        });
    }
  }
}
