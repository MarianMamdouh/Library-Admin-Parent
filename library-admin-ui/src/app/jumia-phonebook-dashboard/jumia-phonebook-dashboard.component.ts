import {CustomerService} from '../services/customer.service';
import {Customer} from '../models/customer';
import { Component, OnInit } from '@angular/core';
import { CountryService } from "../services/country.service";
import { SelectItem, Message, MessageService } from "primeng/api";
import { CustomerFilter } from "../models/customer.filter";

@Component({
    selector: 'jumia-phonebook-dashboard',
    templateUrl: './jumia-phonebook-dashboard.component.html',
    styleUrls: ['./jumia-phonebook-dashboard.component.css']
})
export class JumiaPhonebookDashboardComponent implements OnInit {
    customers: Customer[] = [];
    totalRecords: number = 0;
    pageSize: number = 10;
    rowsPerPageOptions: number[] = [10, 20, 50];
    selectedPage: number = 0;
    countryNamesOptions: SelectItem[] = [];
    stateOptions: SelectItem[] = [];
    customerFilter: CustomerFilter;
    msgs: Message[] = [];

    constructor(private customerService: CustomerService,
                private countryService: CountryService,
                private messageService: MessageService) {
        this.customerFilter = new CustomerFilter();
    }

    ngOnInit() {
        this.filterCustomers();
        this.getCountryNames();
        this.fillStateOptions();
    }

    filterCustomers() {
        let params = "";
        if (this.customerFilter.countryNameFilter) {
            params += "countryName=" + this.customerFilter.countryNameFilter + "&"
        }
        if (this.customerFilter.stateFilter) {
            params += "state=" + this.customerFilter.stateFilter + "&"
        }
        this.clearNotfication();
        return this.customerService.filterCustomers(params, this.selectedPage, this.pageSize)
            .then(response => {
                this.customers = response.content;
                this.totalRecords = response.totalElements;
            })
            .catch(() => {
            this.showError("Retrieving customer data has failed!");
        });
    }

    getCountryNames() {
        this.countryService.getCountryNames()
            .then(response => {
            this.fillCountryNamesOptions(response);
            })
            .catch(() => {
            this.showError("Retrieving country names has failed!");
        });
    }

    fillStateOptions() {
        this.stateOptions = [];
        this.stateOptions.push({label: "Valid", value: "true"});
        this.stateOptions.push({label: "Invalid", value: "false"});
    }

    getPage(event: { page: number; rows: number; }) {
        this.selectedPage = event.page;
        this.pageSize = event.rows;
        this.filterCustomers();
    }

    fillCountryNamesOptions(countryNamesOptions: any[]) {
        this.countryNamesOptions = [];
        countryNamesOptions.forEach(countryNamesOptions => {
            this.countryNamesOptions.push({label: countryNamesOptions, value: countryNamesOptions});
        });
    }

    clearNotfication() {
        this.msgs = [];
    }

    showError(text: string) {
        this.messageService.add({severity:'error', summary:'Error', detail: text});
    }
}
