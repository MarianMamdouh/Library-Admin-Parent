import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {LibraryAdminUrlsConfig} from "../library-admin-urls-config";

@Injectable()
export class StudentService {

  constructor(private http: HttpClient) {
  }

  public getAllCoursesPaymentInfo(): Promise<any> {
    return this.http.get(LibraryAdminUrlsConfig.STUDENT_URL + "/courses/paymentInfos")
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public getAllCoursePapersPaymentInfo(): Promise<any> {
    return this.http.get(LibraryAdminUrlsConfig.STUDENT_URL + "/coursePapers/paymentInfos")
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public searchByPaymentNumber(paymentInfoNumber: number): Promise<any> {
    return this.http.get(LibraryAdminUrlsConfig.STUDENT_URL + "/paymentInfos/search?paymentInfoNumber=" + paymentInfoNumber)
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

}
