import {Component, DestroyRef, OnInit} from '@angular/core';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
import {ActivatedRoute} from '@angular/router';

import {BidService} from "../../../core/service/bid/bid.service";

import {BidModel} from "../../../shared/models/bid.model";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-bid',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './bid.component.html',
  styleUrl: './bid.component.scss'
})
export class BidComponent implements OnInit {

  bidModel?: BidModel;
  id?: string;

  constructor(
    private route: ActivatedRoute,
    private bidService: BidService,
    private destroyRef: DestroyRef
  ) {
  }


  ngOnInit(): void {
    this.getLatestBidByProductId();
  }

  private getLatestBidByProductId(): void {
    this.route.params
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(response => {
        this.id = response['id'];

        this.bidService.getLatestByProductId(this.id || '')
          .pipe(takeUntilDestroyed(this.destroyRef))
          .subscribe(response => this.bidModel = response);
      });
  }
}
