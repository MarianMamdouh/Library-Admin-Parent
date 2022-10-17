import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LibraryAdminUrlsConfig } from '../library-admin-urls-config';
import {Course} from "../models/course";

@Injectable()
export class FacultyService {

  constructor(private http: HttpClient) {
  }

  public getAllFacultyNames(): Promise<any> {
    return this.http.get(LibraryAdminUrlsConfig.FACULTY_NAME_URL, {headers: this.createHeader()})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public createFacultyName(faculty: string): Promise<any> {
    return this.http.post(LibraryAdminUrlsConfig.FACULTY_NAME_URL + "?facultyName=" + faculty, {}, {headers: this.createHeader()})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public deleteCourse(courseName: string): Promise<any> {
    return this.http.delete(LibraryAdminUrlsConfig.COURSES_URL + "?courseName=" + courseName, {headers: this.createHeader()})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }


   private createHeader(contentType?) {
      let headers = new HttpHeaders().set('Authorization', 'Bearer ' + localStorage.getItem("token"));
      if (contentType) {
        headers = headers.append('Content-Type', contentType);
      }
      return headers;
    }
}

