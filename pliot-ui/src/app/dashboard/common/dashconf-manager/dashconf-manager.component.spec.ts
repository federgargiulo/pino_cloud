import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashconfManagerComponent } from './dashconf-manager.component';

describe('DashconfManagerComponent', () => {
  let component: DashconfManagerComponent;
  let fixture: ComponentFixture<DashconfManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashconfManagerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashconfManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
