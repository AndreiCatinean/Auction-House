export interface ProductModel {
  id?: string;
  sellerEmail: string;
  title: string;
  description: string;
  startingPrice: number;
  imageUrl: string;
  startTime?: string;
  endTime?: string;
  status?: string;
  categoryId: number;
}
