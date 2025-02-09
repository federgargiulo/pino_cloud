import { TestBed } from '@angular/core/testing';

import { EquipmentServices } from './equipment.service';

describe('EquipmentServices', () => {
  let service: EquipmentServices;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EquipmentServices);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
