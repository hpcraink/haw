import { StatisticsComponent } from "./statistics.component";

export const routes = [
  { path: '', children: [
    { path: '', component: StatisticsComponent },
    { path: 'stats-details', loadChildren: './+stats-details/stats-details.module#StatsDetailsModule' }
  ]}
];
