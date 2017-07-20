import { Component, OnInit } from '@angular/core';

import { Statistics } from '../statistics.model';
import { StatisticsService } from '../statistics.service';

@Component({
  selector: 'app-statistitcs-list',
  templateUrl: './statistics-list.component.html'
})

export class StatisticsListComponent implements OnInit {
  statisticsArray: Statistics[] = [];

  constructor(private statisticsService: StatisticsService) {}

  ngOnInit() {
    console.log("Hello from statistics-list")
    this.statisticsService.getStats()
      .subscribe((statistics: Statistics[]) => {
        this.statisticsArray = statistics;
      })
  }
}
