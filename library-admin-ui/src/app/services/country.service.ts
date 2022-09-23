import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JumiaUrlsConfig } from '../jumia-urls-config';

@Injectable()
export class CountryService {

    constructor(private http: HttpClient) {
    }

    public getCountryNames(): Promise<any> {
        return this.http.get(JumiaUrlsConfig.COUNTRIES_URL)
            .toPromise()
            .catch(err => {
                return Promise.reject(err.message || err);
            });
    }
}
