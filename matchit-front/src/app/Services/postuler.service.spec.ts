import { TestBed } from '@angular/core/testing';

import { PostulerService } from './postuler.service';

describe('PostulerService', () => {
  let service: PostulerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PostulerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
