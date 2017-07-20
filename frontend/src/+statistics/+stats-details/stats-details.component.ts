import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-stats-details',
  templateUrl: './stats-details.component.html'
})
export class StatsDetailsComponent implements OnInit {

  public ngOnInit() {
    console.log("Hello from StatsDetailsComponent");
  }
}
