import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LibraryAdminUrlsConfig } from '../library-admin-urls-config';
import {CoursePaper} from "../models/coursePaper";

@Injectable()
export class CoursePaperService {

  constructor(private http: HttpClient) {
  }

  public getAllCoursePapers(): Promise<any> {
    return this.http.get(LibraryAdminUrlsConfig.COURSE_PAPERS_URL)
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public updateCoursePaper(coursePaper: CoursePaper): Promise<any> {
    return this.http.put(LibraryAdminUrlsConfig.COURSE_PAPERS_URL, coursePaper)
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public createCoursePaper(coursePaper: CoursePaper): Promise<any> {
    return this.http.post(LibraryAdminUrlsConfig.COURSE_PAPERS_URL, coursePaper)
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public deleteCoursePaper(coursePaperName: string): Promise<any> {
    return this.http.delete(LibraryAdminUrlsConfig.COURSE_PAPERS_URL + "?coursePaperName=" + coursePaperName)
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public searchCoursePapers(coursePaperSearchTerm: string): Promise<any> {
    return this.http.get(LibraryAdminUrlsConfig.COURSE_PAPERS_URL + "/search?searchName=" + coursePaperSearchTerm)
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }
}
