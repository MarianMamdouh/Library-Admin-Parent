import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LibraryAdminUrlsConfig } from '../library-admin-urls-config';
import {Course} from "../models/course";

@Injectable()
export class AcademicYearService {

  constructor(private http: HttpClient) {
  }

  public getAllAcademicYears(): Promise<any> {
    return this.http.get(LibraryAdminUrlsConfig.ACADEMIC_YEAR_URL)
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public createAcademicYear(year: any): Promise<any> {
    return this.http.post(LibraryAdminUrlsConfig.ACADEMIC_YEAR_URL + "?year=" + year, {})
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

