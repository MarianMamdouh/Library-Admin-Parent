import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LibraryAdminUrlsConfig } from '../library-admin-urls-config';
import {Course} from "../models/course";
import {CoursePaper} from "../models/coursePaper";
import {CourseSlot} from "../models/courseSlot";

@Injectable()
export class CourseService {

  constructor(private http: HttpClient) {
  }

  public getAllCourses(): Promise<any> {
    return this.http.get(LibraryAdminUrlsConfig.COURSES_URL, {headers: this.createHeader()})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public updateCourse(course: Course): Promise<any> {
    return this.http.put(LibraryAdminUrlsConfig.COURSES_URL, course, {headers: this.createHeader()})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public addCoursePaperToCourse(courseName: String, coursePaper: CoursePaper): Promise<any> {
    return this.http.put(LibraryAdminUrlsConfig.COURSES_URL + "/" + courseName + "/coursePaper", coursePaper, {headers: this.createHeader()})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public deleteCoursePaperFromCourse(courseName: String, coursePaperName: String): Promise<any> {
    return this.http.delete(LibraryAdminUrlsConfig.COURSES_URL + "/" + courseName + "/coursePaper?coursePaperName=" + coursePaperName, {headers: this.createHeader()})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public deleteCourseSlotFromCourse(courseName: String, courseSlotId: number): Promise<any> {
    return this.http.delete(LibraryAdminUrlsConfig.COURSES_URL + "/" + courseName + "/courseSlot?courseSlotId=" + courseSlotId, {headers: this.createHeader()})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public addCourseSlotToCourse(courseName: String, courseSlot: CourseSlot): Promise<any> {
    return this.http.put(LibraryAdminUrlsConfig.COURSES_URL + "/" + courseName + "/courseSlot", courseSlot, {headers: this.createHeader()})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public createCourse(course: Course): Promise<any> {
    return this.http.post(LibraryAdminUrlsConfig.COURSES_URL, course, {headers: this.createHeader()})
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

  public searchCourses(courseName: string): Promise<any> {
    return this.http.get(LibraryAdminUrlsConfig.COURSES_URL + "/search?searchName=" + courseName, {headers: this.createHeader()})
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

