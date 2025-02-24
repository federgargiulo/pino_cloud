import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserdashboardViewComponent } from './userdashboard-view.component';

describe('UserdashboardViewComponent', () => {
  let component: UserdashboardViewComponent;
  let fixture: ComponentFixture<UserdashboardViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserdashboardViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserdashboardViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
