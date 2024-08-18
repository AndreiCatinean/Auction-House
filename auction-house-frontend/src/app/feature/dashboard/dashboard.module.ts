import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProductsComponent} from "./products/products.component";
import {ProductComponent} from "./product/product.component";
import {FormsModule} from "@angular/forms";
import {ProductCardComponent} from "./product/product-card/product-card.component";
import {DashboardRoutingModule} from './dashboard-routing.module';
import {NavbarComponent} from "../navbar/navbar.component";
import {BidComponent} from "./bid/bid.component";
import {MyTransactionsComponent} from "./my-transactions/my-transactions.component";


@NgModule({
  declarations: [
    ProductsComponent,
    ProductComponent,
    ProductCardComponent,
    MyTransactionsComponent,

  ],
  exports: [
    ProductCardComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    FormsModule,
    NavbarComponent,
    BidComponent,

  ]
})
export class DashboardModule {
}
