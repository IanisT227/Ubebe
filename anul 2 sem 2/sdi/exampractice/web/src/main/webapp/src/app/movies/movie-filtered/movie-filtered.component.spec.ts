import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MovieFilteredComponent} from './movie-filtered.component';

describe('MovieFilteredComponent', () => {
  let component: MovieFilteredComponent;
  let fixture: ComponentFixture<MovieFilteredComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MovieFilteredComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MovieFilteredComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
