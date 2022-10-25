import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';

@Injectable()
export class LoginGuard implements CanActivate {

  constructor(private router: Router) {
}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (localStorage.getItem("token")) {
      this.router.navigate(["/dashboaed"]);

      return false;
    } else {

      // this.router.navigate(["/login"]);
      return true;
    }
  }
}
