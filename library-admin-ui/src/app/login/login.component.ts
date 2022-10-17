import { Component, OnInit } from '@angular/core';
import { Validators,FormControl,FormGroup } from '@angular/forms';
import { LoginService} from "../services/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private loginService: LoginService,
  private router: Router) {
  }


  loginForm: FormGroup;

  submitted = false;

  ngOnInit() {
    this.loginForm = new FormGroup({
      'username': new FormControl('', Validators.required),
      'password': new FormControl('', Validators.required)
    });
  }

  onSubmit() {

    this.submitted = true;
    this.loginService.login(this.loginForm.value.username, this.loginForm.value.password).then(response => {
      localStorage.setItem("token", response.jwtToken);
          this.router.navigate(["/dashboard"]);
    })
      .catch((err) => {

          alert("Wrong username/ password combination");
      });
   }
}
