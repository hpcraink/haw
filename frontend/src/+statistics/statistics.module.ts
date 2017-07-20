import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { StatisticsService } from './statistics.service';
import { StatisticsComponent } from './statistics.component';
import { routes } from './statistics.routes';

console.log('Statistics bundle loaded asynchrounously');

@NgModule({
  declarations: [
    StatisticsComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  providers:[StatisticsService]
})

export class StatisticsModule {
  public static routes = routes;
}
