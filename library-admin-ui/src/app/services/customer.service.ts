import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LibraryAdminUrlsConfig } from '../library-admin-urls-config';

@Injectable()
export class CustomerService {

    constructor(private http: HttpClient) {
    }

    public filterCustomers(params: string, selectedPage?: number, pageSize?: number): Promise<any> {
        return this.http.get(LibraryAdminUrlsConfig.COURSE_PAPERS_URL + params + (!pageSize ? '' : "size=" + pageSize + "&page=" + selectedPage))
            .toPromise()
            .catch(err => {
                return Promise.reject(err.message || err);
            });
    }
}
