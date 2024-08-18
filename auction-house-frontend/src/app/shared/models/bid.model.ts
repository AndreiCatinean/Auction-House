export interface BidModel {
  id?: string;
  productId: string;
  bidderEmail: string;
  bidAmount: number;
  bidTime?: string;
}
