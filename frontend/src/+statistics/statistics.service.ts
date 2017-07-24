import { Http, Response, Headers } from '@angular/http';
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Observable';

import { Statistics } from './statistics.model';
import { ErrorService } from '../errors/error.service';

@Injectable()
export class StatisticsService {
  private statistics: Statistics[] = [];
  private host: string = 'https://backend:3000/statistic';

  constructor(private http:Http, private errorService: ErrorService) {}

  getStats() {
    return this.http.get(this.host)
    .map((response: Response) => {
      const statistics = response.json().obj;
      let newStats: Statistics[] = [];
      for (let stat of statistics) {
        newStats.push(new Statistics(
          stat.Group,
          stat.User,
          stat.JobId,
          stat.TotalRequestedNodes,
          stat.TotalRequestedProcs,
          stat.StartTime,
          stat.EndTime,
          stat.SubmitTime,
          stat.UsedWallTime
        ));
      }
      this.statistics = newStats;
      return newStats;
    }).catch((error: Response) => {
      this.errorService.handleError(error.json());
      return Observable.throw(error.json());
    });
  }
}
