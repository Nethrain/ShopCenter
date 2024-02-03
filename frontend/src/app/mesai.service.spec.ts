import { TestBed } from '@angular/core/testing';

import { MesaiService } from './mesai.service';

describe('MesaiService', () => {
  let service: MesaiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MesaiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
