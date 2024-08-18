import {Component} from '@angular/core';
import {MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle} from '@angular/material/dialog';
import {ProductModel} from "../../../../shared/models/product.model";
import {MatFormField} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {FormsModule} from "@angular/forms";
import {ProductService} from "../../../../core/service/product/product.service";

@Component({
  selector: 'app-add-product-dialog',
  templateUrl: './add-product-dialog.component.html',
  standalone: true,
  imports: [
    MatFormField,
    MatDialogContent,
    MatDialogTitle,
    MatInput,
    MatDialogActions,
    MatButton,
    FormsModule
  ],
  styleUrls: ['./add-product-dialog.component.scss']
})
export class AddProductDialogComponent {
  product: ProductModel = {
    categoryId: 0, imageUrl: "",
    sellerEmail: '',
    title: '',
    description: '',
    startingPrice: 0

  };


  constructor(public dialogRef: MatDialogRef<AddProductDialogComponent>, private productService: ProductService,) {
  }

  onCancelClick(): void {
    this.dialogRef.close();
  }

  onAddClick(): void {
    this.productService.saveProduct(this.product).subscribe(() => {
      this.dialogRef.close(this.product);
    }, (error: any) => {
      console.error('Error saving product:', error);

    });
  }
}
