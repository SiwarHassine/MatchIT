import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ValidationPopupComponentComponent } from './validation-popup-component.component';

describe('ValidationPopupComponentComponent', () => {
  let component: ValidationPopupComponentComponent;
  let fixture: ComponentFixture<ValidationPopupComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ValidationPopupComponentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ValidationPopupComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
