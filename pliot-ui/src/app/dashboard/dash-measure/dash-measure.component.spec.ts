import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashMeasureComponent } from './dash-measure.component';

describe('DashMeasureComponent', () => {
  let component: DashMeasureComponent;
  let fixture: ComponentFixture<DashMeasureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashMeasureComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashMeasureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
