//Angular dependencies
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

//Components
import { AppComponent } from './app.component';
import { JumiaPhonebookDashboardComponent } from './jumia-phonebook-dashboard/jumia-phonebook-dashboard.component';

//Services
import { CustomerService } from './services/customer.service';
import { CountryService } from "./services/country.service";

//Primeng UI Components
import { TableModule } from 'primeng/table';
import { PaginatorModule } from 'primeng/paginator';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { MessagesModule } from 'primeng/messages';
import { MessageService } from 'primeng/api';

@NgModule({
  declarations: [
    AppComponent,
    JumiaPhonebookDashboardComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    TableModule,
    PaginatorModule,
    ButtonModule,
    FormsModule,
    ToastModule,
    MessagesModule
  ],
  providers: [
    CustomerService,
    CountryService,
    MessageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
