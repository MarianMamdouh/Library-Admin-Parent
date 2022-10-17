import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LibraryAdminUrlsConfig } from '../library-admin-urls-config';
import {Course} from "../models/course";
import {CoursePaper} from "../models/coursePaper";
import {CourseSlot} from "../models/courseSlot";

@Injectable()
export class LoginService {

  constructor(private http: HttpClient) {
  }

  public login(username, password): Promise<any> {
    return this.http.post(LibraryAdminUrlsConfig.LOGIN_URL, {username: username, password: password})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }


}

