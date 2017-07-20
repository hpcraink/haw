import { AuthenticationComponent } from './authentication.component';

export const routes = [
  { path: '', children: [
      { path: '', component: AuthenticationComponent },
      { path: 'signin', loadChildren: './+signin/signin.module#SigninModule'},
      { path: 'signup', loadChildren: './+signup/signup.module#SignupModule'},
      { path: 'logout', loadChildren: './+logout/logout.module#LogoutModule'}
  ]}
];
