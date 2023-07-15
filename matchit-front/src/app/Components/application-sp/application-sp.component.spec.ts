import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationSPComponent } from './application-sp.component';

describe('ApplicationSPComponent', () => {
  let component: ApplicationSPComponent;
  let fixture: ComponentFixture<ApplicationSPComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApplicationSPComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationSPComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
