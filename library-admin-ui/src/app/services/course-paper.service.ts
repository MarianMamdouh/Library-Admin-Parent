import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LibraryAdminUrlsConfig } from '../library-admin-urls-config';
import {CoursePaper} from "../models/coursePaper";

@Injectable()
export class CoursePaperService {

  constructor(private http: HttpClient) {
  }

  public getAllCoursePapers(): Promise<any> {
    return this.http.get(LibraryAdminUrlsConfig.COURSE_PAPERS_URL, {headers: this.createHeader()})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public updateCoursePaper(coursePaper: CoursePaper): Promise<any> {
    return this.http.put(LibraryAdminUrlsConfig.COURSE_PAPERS_URL, coursePaper, {headers: this.createHeader()})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public createCoursePaper(coursePaper: CoursePaper): Promise<any> {
    return this.http.post(LibraryAdminUrlsConfig.COURSE_PAPERS_URL, coursePaper, {headers: this.createHeader()})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public deleteCoursePaper(coursePaperName: string): Promise<any> {
    return this.http.delete(LibraryAdminUrlsConfig.COURSE_PAPERS_URL + "?coursePaperName=" + coursePaperName, {headers: this.createHeader()})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public searchCoursePapers(coursePaperSearchTerm: string): Promise<any> {
    return this.http.get(LibraryAdminUrlsConfig.COURSE_PAPERS_URL + "/search?searchName=" + coursePaperSearchTerm, {headers: this.createHeader()})
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
