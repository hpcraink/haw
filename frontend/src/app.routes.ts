import { Routes } from '@angular/router';

import { MessagesComponent } from './messages/messages.component';

export const ROUTES: Routes = [
  { path: '', redirectTo: '/messages', pathMatch: 'full'},
  { path: 'messages', component: MessagesComponent },
  { path: 'auth', loadChildren: './+auth/authentication.module#AuthenticationModule' },
  { path: 'statistics', loadChildren: './+statistics/statistics.module#StatisticsModule' }
];
