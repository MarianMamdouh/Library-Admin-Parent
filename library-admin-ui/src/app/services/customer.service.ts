import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JumiaUrlsConfig } from '../jumia-urls-config';

@Injectable()
export class CustomerService {

    constructor(private http: HttpClient) {
    }

    public filterCustomers(params: string, selectedPage?: number, pageSize?: number): Promise<any> {
        return this.http.get(JumiaUrlsConfig.CUSTOMERS_FILTER_URL + params + (!pageSize ? '' : "size=" + pageSize + "&page=" + selectedPage))
            .toPromise()
            .catch(err => {
                return Promise.reject(err.message || err);
            });
    }
}
