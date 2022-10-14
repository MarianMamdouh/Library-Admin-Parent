import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LibraryAdminUrlsConfig } from '../library-admin-urls-config';
import {Course} from "../models/course";
import {CoursePaper} from "../models/coursePaper";
import {CourseSlot} from "../models/courseSlot";

@Injectable()
export class CourseService {

  constructor(private http: HttpClient) {
  }

  public getAllCourses(): Promise<any> {
    return this.http.get(LibraryAdminUrlsConfig.COURSES_URL)
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public updateCourse(course: Course): Promise<any> {
    return this.http.put(LibraryAdminUrlsConfig.COURSES_URL, course)
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public addCoursePaperToCourse(courseName: String, coursePaper: CoursePaper): Promise<any> {
    return this.http.put(LibraryAdminUrlsConfig.COURSES_URL + "/" + courseName + "/coursePaper", coursePaper)
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public deleteCoursePaperFromCourse(courseName: String, coursePaperName: String): Promise<any> {
    return this.http.delete(LibraryAdminUrlsConfig.COURSES_URL + "/" + courseName + "/coursePaper?coursePaperName=" + coursePaperName)
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public deleteCourseSlotFromCourse(courseName: String, courseSlotId: number): Promise<any> {
    return this.http.delete(LibraryAdminUrlsConfig.COURSES_URL + "/" + courseName + "/courseSlot?courseSlotId=" + courseSlotId)
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public addCourseSlotToCourse(courseName: String, courseSlot: CourseSlot): Promise<any> {
    return this.http.put(LibraryAdminUrlsConfig.COURSES_URL + "/" + courseName + "/courseSlot", courseSlot)
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public createCourse(course: Course): Promise<any> {
    return this.http.post(LibraryAdminUrlsConfig.COURSES_URL, course)
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

