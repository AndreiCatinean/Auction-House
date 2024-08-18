import {Component, Inject} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from '@angular/material/dialog';
import {BidService} from "../../../../core/service/bid/bid.service";
import {BidModel} from "../../../../shared/models/bid.model";

import {AddProductDialogComponent} from "../../products/add-product-dialog/add-product-dialog.component";

import {FormsModule} from "@angular/forms";
import {MatFormField} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";


@Component({
  selector: 'app-add-bid-modal',
  templateUrl: './add-bid-modal.component.html',
  standalone: true,
  imports: [
    FormsModule,
    MatDialogContent,
    MatDialogTitle,
    MatFormField,
    MatInput,
    MatDialogActions,
    MatButton
  ],
  styleUrls: ['./add-bid-modal.component.scss']
})
export class AddBidModalComponent {


  bid: BidModel = {
    bidAmount: 0, bidderEmail: "", productId: ""

  };


  constructor(
    public dialogRef: MatDialogRef<AddProductDialogComponent>,
    private bidService: BidService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
  }

  onCancelClick(): void {
    this.dialogRef.close();
  }

  onAddClick(): void {
    this.bid.productId = this.data.productId;
    this.bidService.addBid(this.bid).subscribe(() => {
      this.dialogRef.close({success: true});
    }, (error: any) => {
      console.error('Error saving bid:', error);
      this.dialogRef.close({success: false, error: error.message});
    });
  }
}
