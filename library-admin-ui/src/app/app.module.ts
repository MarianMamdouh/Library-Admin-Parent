//Angular dependencies
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

//Components
import { AppComponent } from './app.component';
import { LibraryAdminDashboardComponent } from './library-admin-dashboard/library-admin-dashboard.component';

//Services
import { CustomerService } from './services/customer.service';
import { CourseService } from "./services/course.service";

//Primeng UI Components
import { TableModule } from 'primeng/table';
import { PaginatorModule } from 'primeng/paginator';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { MessagesModule } from 'primeng/messages';
import {ConfirmationService, MessageService} from 'primeng/api';
import {TabViewModule} from 'primeng/tabview';
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {ToolbarModule} from "primeng/toolbar";
import {FileUploadModule} from "primeng/fileupload";
import {DialogModule} from "primeng/dialog";
import {RadioButtonModule} from "primeng/radiobutton";
import {ProductService} from "./services/product.service";
import {RatingModule} from "primeng/rating";

import {CalendarModule} from 'primeng/calendar';
import {SliderModule} from 'primeng/slider';
import {MultiSelectModule} from 'primeng/multiselect';
import {ContextMenuModule} from 'primeng/contextmenu';
import {DropdownModule} from 'primeng/dropdown';
import {ProgressBarModule} from 'primeng/progressbar';
import {InputTextModule} from 'primeng/inputtext';
import {InputNumberModule} from 'primeng/inputnumber';
import { InputTextareaModule } from 'primeng/inputtextarea';
import {CoursePaperService} from "./services/course-paper.service";
import {AcademicYearService} from "./services/academic-year.service";
import {FacultyService} from "./services/faculty.service";

@NgModule({
  declarations: [
    AppComponent,
    LibraryAdminDashboardComponent
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
    MessagesModule,
    TabViewModule,
    ConfirmDialogModule,
    ToolbarModule,
    FileUploadModule,
    DialogModule,
    RadioButtonModule,
    RatingModule,
    CalendarModule,
    InputTextareaModule,
    InputNumberModule,
    InputTextareaModule,
    InputTextModule,
    ProgressBarModule,
    DropdownModule,
    ContextMenuModule,
    MultiSelectModule,
    SliderModule
  ],
  providers: [
    CustomerService,
    CourseService,
    MessageService,
    ProductService,
    ConfirmationService,
    CoursePaperService,
    AcademicYearService,
    FacultyService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }