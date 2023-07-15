import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OffersRecrutorComponent } from './offers-recrutor.component';

describe('OffersRecrutorComponent', () => {
  let component: OffersRecrutorComponent;
  let fixture: ComponentFixture<OffersRecrutorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OffersRecrutorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OffersRecrutorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
