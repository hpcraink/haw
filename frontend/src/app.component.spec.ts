import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { By }     from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { AppComponent } from './app.component';

describe('AppComponent', ()=> {
  let comp:         AppComponent;
  let fixture:      ComponentFixture<AppComponent>;
  let de:           DebugElement;
  let el:           HTMLElement;

  // async beforeEach
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppComponent ],     // declare the test component
    })
    .compileComponents(); // compile template and css
  }));

  // synchronous beforeEach
  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);

    comp = fixture.componentInstance; // AppComponent test instance

    // query for the title
  })
});
