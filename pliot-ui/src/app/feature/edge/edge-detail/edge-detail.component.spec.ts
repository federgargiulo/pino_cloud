import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EdgeDetailComponent } from './edge-detail.component';

describe('EdgeDetailComponent', () => {
  let component: EdgeDetailComponent;
  let fixture: ComponentFixture<EdgeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EdgeDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EdgeDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
