import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CommonModule} from '@angular/common';
import {LibraryAdminDashboardComponent} from './library-admin-dashboard/library-admin-dashboard.component';
import {LoginComponent} from './login/login.component';
import {AuthGuard} from './auth.guard';

const routes: Routes = [

  {
      path: "",
      component: LoginComponent
    },
    {
    path: "dashboard",
    component: LibraryAdminDashboardComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule],

  declarations: []
})
export class AppRoutingModule {
}
