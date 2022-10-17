import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {LibraryAdminUrlsConfig} from "../library-admin-urls-config";

@Injectable()
export class StudentService {

  constructor(private http: HttpClient) {
  }

  public getAllCoursesPaymentInfo(): Promise<any> {
    return this.http.get(LibraryAdminUrlsConfig.STUDENT_URL + "/courses/paymentInfos", {headers: this.createHeader()})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

  public getAllCoursePapersPaymentInfo(): Promise<any> {
    return this.http.get(LibraryAdminUrlsConfig.STUDENT_URL + "/coursePapers/paymentInfos", {headers: this.createHeader()})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }

    public deleteCoursePaperPaymentInfo(paymentNumber): Promise<any> {
      return this.http.delete(LibraryAdminUrlsConfig.STUDENT_URL + "/coursePapers/paymentInfos?paymentInfoNumber=" + paymentNumber, {headers: this.createHeader()})
        .toPromise()
        .catch(err => {
          return Promise.reject(err.message || err);
        });
    }

  public deleteCoursePaymentInfo(paymentNumber): Promise<any> {
    return this.http.delete(LibraryAdminUrlsConfig.STUDENT_URL + "/courses/paymentInfos?paymentInfoNumber=" + paymentNumber, {headers: this.createHeader()})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }



  public searchByPaymentNumber(paymentInfoNumber: number): Promise<any> {
    return this.http.get(LibraryAdminUrlsConfig.STUDENT_URL + "/paymentInfos/search?paymentInfoNumber=" + paymentInfoNumber, {headers: this.createHeader()})
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

  public filterByDeliveryAddress(): Promise<any> {
    return this.http.get(LibraryAdminUrlsConfig.STUDENT_URL + "/filterByDeliveryAddress", {headers: this.createHeader()})
      .toPromise()
      .catch(err => {
        return Promise.reject(err.message || err);
      });
  }


}
