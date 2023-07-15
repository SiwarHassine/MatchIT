import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResetNewpasswordComponent } from './reset-newpassword.component';

describe('ResetNewpasswordComponent', () => {
  let component: ResetNewpasswordComponent;
  let fixture: ComponentFixture<ResetNewpasswordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResetNewpasswordComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetNewpasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
