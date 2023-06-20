import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ViewUsersComponent} from './pages/view-users/view-users.component';
import {CreateAccountComponent} from './pages/create-account/create-account.component';
import {LoginComponent} from './pages/login/login.component';
import {UserDetailComponent} from './pages/user-detail/user-detail.component';


const routes: Routes = [

  {
    path: 'accounts/:id',
    component: CreateAccountComponent
  } ,
  {
    path: 'users/:id/details',
    component: UserDetailComponent
  } ,
  {
    path: 'users',
    component: ViewUsersComponent
  } ,
  {
    path: 'login',
    component: LoginComponent
  } ,
  {
    path: '',
    redirectTo : 'login',
    pathMatch : 'full'
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
