//Angular dependencies
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppRoutingModule} from './app-routing.module';

//Components
import { AppComponent } from './app.component';
import { LibraryAdminDashboardComponent } from './library-admin-dashboard/library-admin-dashboard.component';
import { LoginComponent } from './login/login.component';

//Services
import { CourseService } from "./services/course.service";
import { LoginService } from "./services/login.service";
import {CoursePaperService} from "./services/course-paper.service";
import {AcademicYearService} from "./services/academic-year.service";
import {FacultyService} from "./services/faculty.service";
import {AuthGuard} from './auth.guard';

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
import {StudentService} from "./services/student.service";
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
import {InputSwitchModule} from 'primeng/inputswitch';

import {PanelModule} from "primeng/panel";
// import { AmplifyAuthenticatorModule } from '@aws-amplify/ui-angular';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
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
    SliderModule,
    PanelModule,
    ReactiveFormsModule,
    AppRoutingModule,
    InputSwitchModule
  ],
  providers: [
    CourseService,
    MessageService,
    StudentService,
    ConfirmationService,
    CoursePaperService,
    AcademicYearService,
    FacultyService,
    StudentService,
    LoginService,
    AuthGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
