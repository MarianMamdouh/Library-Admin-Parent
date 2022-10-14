import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LibraryAdminUrlsConfig } from '../library-admin-urls-config';
import {Course} from "../models/course";

@Injectable()
export class FacultyService {

  constructor(private http: HttpClient) {
  }

  public getAllFacultyNames(): Promise<any> {
    return this.http.get(LibraryAdminUrlsConfig.FACULTY_NAME_URL)
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public createFacultyName(faculty: string): Promise<any> {
    return this.http.post(LibraryAdminUrlsConfig.FACULTY_NAME_URL + "?facultyName=" + faculty, {})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public deleteCourse(courseName: string): Promise<any> {
    return this.http.delete(LibraryAdminUrlsConfig.COURSES_URL + "?courseName=" + courseName)
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }
}

