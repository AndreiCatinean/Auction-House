import {ProductModel} from "./product.model";
import {TransactionModel} from "./transaction.model";

export interface TransactionInformationModel {
  transaction: TransactionModel;
  product: ProductModel;
}
