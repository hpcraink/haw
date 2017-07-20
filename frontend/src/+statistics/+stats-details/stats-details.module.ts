import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { routes } from './stats-details.routes';
import { StatisticsListComponent } from './statistics-list.component';
import { StatisticsRowComponent } from './statistics-row.component';
import { StatsDetailsComponent } from './stats-details.component';

@NgModule({
  declarations: [
    StatsDetailsComponent,
    StatisticsListComponent,
    StatisticsRowComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ]
})
export class StatsDetailsModule {
  public static routes = routes;
}
