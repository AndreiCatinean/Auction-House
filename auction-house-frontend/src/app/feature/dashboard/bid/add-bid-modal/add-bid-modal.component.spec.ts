import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AddBidModalComponent} from './add-bid-modal.component';

describe('AddBidModalComponent', () => {
  let component: AddBidModalComponent;
  let fixture: ComponentFixture<AddBidModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddBidModalComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(AddBidModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
