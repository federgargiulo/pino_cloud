import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailEquipmentComponent } from './detail-equipment.component';

describe('DetailEquipmentComponent', () => {
  let component: DetailEquipmentComponent;
  let fixture: ComponentFixture<DetailEquipmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DetailEquipmentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailEquipmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
