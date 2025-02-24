import { TestBed } from '@angular/core/testing';

import { TenantServices } from './equipment.service';

describe('TenantServices', () => {
  let service: TenantServices;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EquipmentServices);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
