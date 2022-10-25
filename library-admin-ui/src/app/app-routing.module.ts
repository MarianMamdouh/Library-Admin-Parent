import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CommonModule} from '@angular/common';
import {LibraryAdminDashboardComponent} from './library-admin-dashboard/library-admin-dashboard.component';
import {LoginComponent} from './login/login.component';
import {AuthGuard} from './auth.guard';
import {LoginGuard} from "./login.guard";

const routes: Routes = [

  {
      path: "login",
      component: LoginComponent,
      canActivate: [LoginGuard]
    },
    {
    path: "dashboard",
    component: LibraryAdminDashboardComponent,
      canActivate: [AuthGuard]
  },
  {
    path: "",
    redirectTo: "/dashboard",
    pathMatch: "full"
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
