import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchEquipmentComponent } from './search-equipment.component';

describe('SearchEquipmentComponent', () => {
  let component: SearchEquipmentComponent;
  let fixture: ComponentFixture<SearchEquipmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SearchEquipmentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchEquipmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
