import { Component, OnInit } from "@angular/core";
import { CourseService } from "../services/course.service";
import { CoursePaperService } from "../services/course-paper.service";
import { ConfirmationService, Message, MessageService } from "primeng/api";
import { StudentService } from "../services/student.service";
import { Course } from "../models/course";
import { CoursePaper } from "../models/coursePaper";
import { AcademicYearService } from "../services/academic-year.service";
import { FacultyService } from "../services/faculty.service";
import { CourseSlot } from "../models/courseSlot";
import { PaymentInfo } from "../models/paymentInfo";
import {Faculty} from "../models/faculty";
import {AcademicYear} from "../models/academicYear";

@Component({
  selector: "library-admin-dashboard",
  templateUrl: "./library-admin-dashboard.component.html",
  styles: [
    `
    .p-dialog .product-image {
      width: 150px;
      margin: 0 auto 2rem auto;
      display: block;
    }
  `,
  ],
  styleUrls: ["./library-admin-dashboard.component.scss"],
})
export class LibraryAdminDashboardComponent implements OnInit {
  rowsPerPageOptions: number[] = [10, 20, 50];
  msgs: Message[] = [];

  weekDays: String[] = [
    "SATURDAY",
    "SUNDAY",
    "MONDAY",
    "TUESDAY",
    "WEDNESDAY",
    "THURSDAY",
    "FRIDAY",
  ];

  courses: Course[] = [];

  course: Course;

  courseSlotOne: CourseSlot;

  coursePapers: CoursePaper[] = [];

  coursePaperNames: string[] = [];

  academicYearNames: number[] = [];

  facultyNamesNames: string[] = [];

  selectedCoursePaperNames: string[] = [];

  selectedCourseSlotDays: string[] = [];

  coursePaper: CoursePaper;

  academicYears: AcademicYear[] = [];

  facultyNames: Faculty[] = [];

  coursesPaymentInfos: PaymentInfo[] = [];

  coursePapersPaymentInfos: PaymentInfo[] = [];

  studentAssignedCourses: PaymentInfo[] = [];

  studentAssignedCoursePapers: PaymentInfo[] = [];

  courseDialog: boolean;

  coursePaperDialog: boolean;

  assignCoursePaperDialog: boolean;

  editCourseDialog: boolean;

  academicYearDialog: boolean;

  facultyNameDialog: boolean;

  courseSlotsDialog: boolean;

  isCoursePaperNameDisabled: boolean;

  addToCourse: boolean;

  submitted: boolean = false;

  academicYearValue: Date;
  facultyName: string;

  isEditCourseDialogSubmitted: boolean;
  isCoursePaperDialogueSubmitted: boolean;
  isAssignCoursePaperDialogueSubmitted: boolean;
  isAcademicYearDialogueSubmitted: boolean;
  isFacultyNameDialogueSubmitted: boolean;
  isCourseSlotDialogueSubmitted: boolean;

  statuses: any[];

  constructor(
    private courseService: CourseService,
    private messageService: MessageService,
    private studentService: StudentService,
    private confirmationService: ConfirmationService,
    private coursePaperService: CoursePaperService,
    private academicYearService: AcademicYearService,
    private facultyService: FacultyService
  ) {}

  ngOnInit() {
    this.getAllCourses();
    this.getAllCoursePapers();
    this.getAllAcademicYears();
    this.getAllFacultyNames();
    this.getAllStudentCoursesPaymentInfo();
    this.getAllStudentCoursePapersPaymentInfo();
    this.getAllStudentAssignedCourses();
    this.getAllStudentAssignedCoursePapers();
  }


  deleteCoursePaperPaymentInfo(coursePapersPaymentInfo) {
    this.confirmationService.confirm({
      message:
        "Are you sure you want to delete payment with number " + coursePapersPaymentInfo.paymentNumber,
      header: "Confirm",
      icon: "pi pi-exclamation-triangle",
      accept: () => {

        this.studentService
          .deleteCoursePaperPaymentInfo(coursePapersPaymentInfo.paymentNumber)
          .then((response) => {
            this.coursePapersPaymentInfos =
              this.coursePapersPaymentInfos.filter(
                (val) =>
                  val.paymentNumber !== coursePapersPaymentInfo.paymentNumber
              );
            this.coursePapersPaymentInfos = [...this.coursePapersPaymentInfos];
          })
          .catch(() => {
            this.showError("Deleting payment info has failed");
          });
      },
    });
  }

  unassignStudentFromCoursePaper(coursePaperPaymentInfo) {

    this.confirmationService.confirm({
      message:
        "Are you sure you want to unassign student with number " + coursePaperPaymentInfo.mobileNumber + " from course paper" + coursePaperPaymentInfo.coursePaperName + "?",
      header: "Confirm",
      icon: "pi pi-exclamation-triangle",
      accept: () => {

        this.studentService
          .unassignStudentFromCoursePaper(coursePaperPaymentInfo.coursePaperName, coursePaperPaymentInfo.mobileNumber)
          .then((response) => {

              let payemntInfo = this.studentAssignedCoursePapers.filter(
                (val) =>
                  val.mobileNumber == coursePaperPaymentInfo.mobileNumber
              );

              if (payemntInfo.length > 1) {
                let payemntInfoIndex = this.studentAssignedCoursePapers.findIndex(
                  (val) =>
                    val.mobileNumber == coursePaperPaymentInfo.mobileNumber
                );

                this.studentAssignedCoursePapers.splice(payemntInfoIndex, 1);
              }
              else {
                this.studentAssignedCoursePapers =

                  this.studentAssignedCoursePapers.filter(
                    (val) =>
                      val.mobileNumber !== coursePaperPaymentInfo.mobileNumber
                  );
              }

            this.studentAssignedCoursePapers = [...this.studentAssignedCoursePapers];
          })
          .catch(() => {
            this.showError("Unassigning student has failed");
          });
      },
    });

  }

  unassignStudentFromCourse(coursePaymentInfo) {

    this.confirmationService.confirm({
      message:
        "Are you sure you want to unassign student with number " + coursePaymentInfo.mobileNumber + " from course " + coursePaymentInfo.courseName + "?",
      header: "Confirm",
      icon: "pi pi-exclamation-triangle",
      accept: () => {

        this.studentService
          .unassignStudentFromCourse(coursePaymentInfo.courseName, coursePaymentInfo.mobileNumber)
          .then((response) => {
            this.studentAssignedCourses =
              this.studentAssignedCourses.filter(
                (val) =>
                  val.mobileNumber !== coursePaymentInfo.mobileNumber
              );
            this.studentAssignedCourses = [...this.studentAssignedCourses];
          })
          .catch(() => {
            this.showError("Unassigning student has failed");
          });
      },
    });

  }

  deleteCoursePaymentInfo(coursePaymentInfo) {
    this.confirmationService.confirm({
      message:
        "Are you sure you want to delete payment with number " + coursePaymentInfo.paymentNumber,
      header: "Confirm",
      icon: "pi pi-exclamation-triangle",
      accept: () => {

        this.studentService
          .deleteCoursePaymentInfo(coursePaymentInfo.paymentNumber)
          .then((response) => {
            this.coursesPaymentInfos =
              this.coursesPaymentInfos.filter(
                (val) =>
                  val.paymentNumber !== coursePaymentInfo.paymentNumber
              );
            this.coursesPaymentInfos = [...this.coursesPaymentInfos];
          })
          .catch(() => {
            this.showError("Deleting payment info has failed");
          });
      },
    });
  }

  getAllCourses() {
    this.courseService
      .getAllCourses()
      .then((response) => {
        this.courses = response.content;
      })
      .catch(() => {
        this.showError("Retrieving courses has failed!");
      });
  }

  getAllCoursePapers() {
    this.coursePaperService
      .getAllCoursePapers()
      .then((response) => {
        this.coursePapers = response.content;
        this.coursePaperNames = this.coursePapers.map(
          (value) => value.coursePaperName
        );
      })
      .catch(() => {
        this.showError("Retrieving Course Papers has failed!");
      });
  }

  searchCourses(searchTerm: string) {
    if (!searchTerm) {
      return this.getAllCourses();
    }

    this.courseService
      .searchCourses(searchTerm)
      .then((response) => {
        this.courses = response.content;
      })
      .catch(() => {
        this.showError("Retrieving Course has failed!");
      });
  }

  searchCoursePapers(searchTerm) {
    if (!searchTerm) {
      return this.getAllCoursePapers();
    }

    this.coursePaperService
      .searchCoursePapers(searchTerm)
      .then((response) => {
        this.coursePapers = response;
      })
      .catch(() => {
        this.showError("Retrieving Course Papers has failed!");
      });
  }

  searchByCoursePaymentInfoNumber(paymentInfoNumber) {

    if (!paymentInfoNumber) {
      this.getAllStudentCoursesPaymentInfo();
      return;
    }

    this.studentService
      .searchByPaymentNumber(paymentInfoNumber)
      .then((response) => {
        this.coursesPaymentInfos = [];
        this.coursesPaymentInfos.push(response);
      })
      .catch(() => {
        this.showError("Retrieving Payment Infos has failed!");
      });
  }

  searchByCoursePapersPaymentInfoNumber(paymentInfoNumber) {

    if (!paymentInfoNumber) {
      this.getAllStudentCoursePapersPaymentInfo();
      return;
    }
    this.studentService
      .searchByPaymentNumber(paymentInfoNumber)
      .then((response) => {

        this.coursePapersPaymentInfos = [];
        this.coursePapersPaymentInfos.push(response);
      })
      .catch(() => {
        this.showError("Retrieving Payment Infos has failed!");
      });
  }

  getAllAcademicYears() {
    this.academicYearService
      .getAllAcademicYears()
      .then((response) => {

        let responseAcademicYears : AcademicYear[] = [];
        response.forEach(function (year) {
            let academicYear = {year: year}
          responseAcademicYears.push(academicYear)
          }
        );
        this.academicYears = [...responseAcademicYears];

        this.academicYearNames = this.academicYears.map(
          (value) => value.year
        );
      })
      .catch(() => {
        this.showError("Retrieving academic years has failed!");
      });
  }

  getAllFacultyNames() {
    this.facultyService
      .getAllFacultyNames()
      .then((response) => {

        let responseFaculties : Faculty[] = [];
        response.content.forEach(function (name) {
            let facultyName = {name: name}
          responseFaculties.push(facultyName)
          }
        );
         this.facultyNames = [...responseFaculties];
        this.facultyNamesNames = this.facultyNames.map(
          (value) => value.name
        );
      })
      .catch(() => {
        this.showError("Retrieving faculty names has failed!");
      });
  }

  getAllStudentCoursesPaymentInfo() {

    this.studentService
      .getAllCoursesPaymentInfo()
      .then((response) => {
        this.coursesPaymentInfos = response.content;
      })
      .catch(() => {
        this.showError("Retrieving payment infos has failed!");
      });
  }

  getAllStudentCoursePapersPaymentInfo() {

    this.studentService
      .getAllCoursePapersPaymentInfo()
      .then((response) => {
        this.coursePapersPaymentInfos = response.content;
      })
      .catch(() => {
        this.showError("Retrieving payment infos has failed!");
      });
  }

  getAllStudentAssignedCourses() {

    this.studentService
      .getAllAssignedCourses()
      .then((response) => {
        this.studentAssignedCourses = response.content;
      })
      .catch(() => {
        this.showError("Retrieving Student Assigned Courses has failed!");
      });
  }

  getAllStudentAssignedCoursePapers() {

    this.studentService
      .getAllAssignedCoursePapers()
      .then((response) => {
        this.studentAssignedCoursePapers = response.content;
      })
      .catch(() => {
        this.showError("Retrieving Student Assigned Course Papers has failed!");
      });
  }

  filterByDeliveryAddress() {

    this.studentService
      .filterByDeliveryAddress()
      .then((response) => {
        this.coursePapersPaymentInfos = response.content;
      })
      .catch(() => {
        this.showError("Filtering payment infos has failed!");
      });
  }

  openNew() {
    this.course = { coursePapers: [], courseSlots: [] };
    this.submitted = false;
    this.courseDialog = true;
  }

  openNewCoursePaperDialogue() {

    this.isCoursePaperNameDisabled = false;
    this.coursePaper = {};
    this.isCoursePaperDialogueSubmitted = false;
    this.coursePaperDialog = true;
  }


  OpenNewCourseSlotDialog() {
    this.courseSlotOne = {};
    this.submitted = true;

    if (
      !this.course.courseName ||
      !this.course.professorName ||
      !this.course.subjectName ||
      !this.course.facultyName ||
      !this.course.academicYear ||
      !this.course.pricePerMonth ||
      !this.course.pricePerSemester) {
      return;
    }

    this.courseDialog = false;
    this.courseSlotsDialog = true;
  }

  OpenNewCourseCourseSlotDialog(course: Course) {

    this.courseSlotOne = {};
    this.addToCourse = true;
    this.isCourseSlotDialogueSubmitted = false;
    this.courseSlotsDialog = true;
    this.course = course;
  }

  openAssignNewCoursePaperDialogue(course) {

    this.course = course;
    this.isAssignCoursePaperDialogueSubmitted = false;
    this.assignCoursePaperDialog = true;
  }

  openNewCourseCoursePaperDialogue(course: Course) {

    this.isCoursePaperNameDisabled = false;
    this.addToCourse = true;
    this.coursePaper = {};
    this.isCoursePaperDialogueSubmitted = false;
    this.coursePaperDialog = true;
    this.course = course;
  }

  openNewEditCourseDialogue(course: Course) {

    this.course = course;
    this.isEditCourseDialogSubmitted = false;
    this.editCourseDialog = true;
  }

  openNewAcademicYearDialogue() {

    this.academicYearValue = undefined;
    this.isAcademicYearDialogueSubmitted = false;
    this.academicYearDialog = true;
  }

  openNewFacultyNameDialogue() {

    this.facultyName = undefined;
    this.isFacultyNameDialogueSubmitted = false;
    this.facultyNameDialog = true;
  }

  editCoursePaper(coursePaper: CoursePaper) {

    this.isCoursePaperNameDisabled = true;
    this.coursePaper = {};
    this.coursePaper = coursePaper;
    this.coursePaperDialog = true;
  }

  deleteCourse(course: Course) {
    this.confirmationService.confirm({
      message: "Are you sure you want to delete " + course.courseName + "?",
      header: "Confirm",
      icon: "pi pi-exclamation-triangle",
      accept: () => {
        this.courseService
          .deleteCourse(course.courseName)
          .then(() => {
            this.courses = this.courses.filter(
              (val) => val.courseName !== course.courseName
            );
            this.courses = [...this.courses];
            this.messageService.add({
              severity: "success",
              summary: "Successful",
              detail: "Course Deleted",
              life: 3000,
            });
            this.course = { coursePapers: [], courseSlots: [] };
          })
          .catch(() => {
            this.showError("Deleting course has failed!");
          });
      },
    });
  }

  deleteCoursePaper(coursePaper: CoursePaper) {
    this.confirmationService.confirm({
      message:
        "Are you sure you want to delete " + coursePaper.coursePaperName + "?",
      header: "Confirm",
      icon: "pi pi-exclamation-triangle",
      accept: () => {
        this.coursePaperService
          .deleteCoursePaper(coursePaper.coursePaperName)
          .then(() => {
            this.coursePapers = this.coursePapers.filter(
              (val) => val.coursePaperName !== coursePaper.coursePaperName
            );
            this.coursePapers = [...this.coursePapers];

            this.coursePaperNames = this.coursePaperNames.filter(
              (val) => val !== coursePaper.coursePaperName
            );
            this.coursePaperNames = [...this.coursePaperNames];
            console.log(this.coursePaperNames);
            this.messageService.add({
              severity: "success",
              summary: "Successful",
              detail: "Course Paper Deleted successfully",
              life: 3000,
            });
            this.coursePaper = {};
          })
          .catch(() => {
            this.showError("Deleting course paper has failed!");
          });
      },
      reject: (type) => {
        this.messageService.add({
          severity: "warn",
          summary: "Cancelled",
          detail: "You have cancelled",
        });
      },
    });
  }

  deleteCoursePaperFromCourse(courseName, coursePaperName) {
    this.confirmationService.confirm({
      message: "Are you sure you want to delete " + coursePaperName + "?",
      header: "Confirm",
      icon: "pi pi-exclamation-triangle",
      accept: () => {
        this.courseService
          .deleteCoursePaperFromCourse(courseName, coursePaperName)
          .then(() => {

            let coursename = this.courses.find(
              (value) => value.courseName == courseName
            );

            let courseIndex = this.courses.findIndex(
              (value) => value.courseName == courseName
            );

            this.courses.splice(courseIndex, 1);

            let newCourse: Course = coursename;

            let coursePaperIndex = newCourse.coursePapers.findIndex(
              (value) => value.coursePaperName == coursePaperName
            );

            newCourse.coursePapers.splice(coursePaperIndex, 1);
            this.courses.push(newCourse);


            this.messageService.add({
              severity: "success",
              summary: "Successful",
              detail: "Course Paper Deleted successfully",
              life: 3000,
            });
            this.coursePaper = {};
          })
          .catch(() => {
            this.showError("Deleting course paper has failed!");
          });
      },
    });
  }

  deleteCourseSlotFromCourse(courseName: string, courseSlot: CourseSlot) {
    this.confirmationService.confirm({
      message: "Are you sure you want to delete this course slot?",
      header: "Confirm",
      icon: "pi pi-exclamation-triangle",
      accept: () => {
        this.courseService
          .deleteCourseSlotFromCourse(courseName, courseSlot.id)
          .then(() => {
            // console.log(this.course.courseSlots);
            // let course = this.course.coursePapers.filter(val => val.coursePaperName !== coursePaperName);
            // this.coursePapers = [...this.coursePapers];
            //
            // this.coursePaperNames = this.coursePaperNames.filter(val => val !== coursePaper.coursePaperName);
            // this.coursePaperNames = [...this.coursePaperNames];
            // console.log(course);
            this.messageService.add({
              severity: "success",
              summary: "Successful",
              detail: "Course Slot Deleted successfully",
              life: 3000,
            });
            this.coursePaper = {};
          })
          .catch(() => {
            this.showError("Deleting course paper has failed!");
          });
      },
      reject: (type) => {
        this.messageService.add({
          severity: "warn",
          summary: "Cancelled",
          detail: "You have cancelled",
        });
      },
    });
  }

  deleteAcademicYear(academicYear) {
    this.confirmationService.confirm({
      message: "Are you sure you want to delete " + academicYear.year + "?",
      header: "Confirm",
      icon: "pi pi-exclamation-triangle",
      accept: () => {
        this.academicYearService
          .deleteAcademicYear(academicYear.year)
          .then(() => {


            let academicYearIndex = this.academicYears.findIndex(
              (value) => value.year == academicYear.year
            );

            this.academicYears.splice(academicYearIndex, 1);

            let academicYearNameIndex = this.academicYearNames.findIndex(
              (value) => value == academicYear.year
            );

            console.log(academicYearIndex);

            this.academicYearNames.splice(academicYearNameIndex, 1);

            this.messageService.add({
              severity: "success",
              summary: "Successful",
              detail: "Academic Year Deleted successfully",
              life: 3000,
            });
          })
          .catch(() => {
            this.showError("Deleting Academic Year has failed!");
          });
      },
    });


  }
  hideDialog() {
    this.courseDialog = false;
    this.submitted = false;
    this.coursePaperNames = [];
  }

  hideCourseSlotDialog() {
    // this.addToCourse = false;
    this.courseSlotsDialog = false;
    this.courseDialog = true;
    this.isCourseSlotDialogueSubmitted = false;
    // this.courseSlotOne = {};
  }

  hideCourseCourseSlotDialog() {
    this.addToCourse = false;
    this.courseSlotsDialog = false;
    this.isCourseSlotDialogueSubmitted = false;
  }

  hideCoursePaperDialog() {
    this.coursePaperDialog = false;
    this.isCoursePaperDialogueSubmitted = false;
  }

  hideAcademicYearDialog() {
    this.academicYearDialog = false;
    this.isAcademicYearDialogueSubmitted = false;
  }

  hideFacultyNameDialog() {
    this.facultyNameDialog = false;
    this.isFacultyNameDialogueSubmitted = false;
  }

  hideAssignCoursePaperDialog() {
    this.assignCoursePaperDialog = false;
    this.isAssignCoursePaperDialogueSubmitted = false;
  }

  hideEditCourseDialog() {
    this.editCourseDialog = false;
    this.isEditCourseDialogSubmitted = false;
  }

  editCourse() {
    this.isEditCourseDialogSubmitted = true;

    if (
      !this.course.courseName ||
      !this.course.professorName ||
      !this.course.subjectName ||
      !this.course.facultyName ||
      !this.course.academicYear ||
      !this.course.pricePerMonth ||
      !this.course.pricePerSemester
    ) {
      return;
    }

    this.courseService
      .updateCourse(this.course)
      .then(() => {
        console.log(this.courses);

        for (let i = 0; i < this.courses.length; i++) {
          if (this.courses[i].courseName === this.course.courseName) {
            this.courses[i] = this.course;
          }
        }
        this.courses = [...this.courses];
        this.messageService.add({
          severity: "success",
          summary: "Successful",
          detail: "Course is updated successfully",
          life: 3000,
        });
        this.course = { coursePapers: [], courseSlots: [] };
      })
      .catch(() => {
        this.showError("Updating courses has failed!");
      });

    this.editCourseDialog = false;
    this.isEditCourseDialogSubmitted = false;
  }

  addCourseSlotToCourse() {
    this.isCourseSlotDialogueSubmitted = true;

    if (
      !this.selectedCourseSlotDays ||
      !this.courseSlotOne.startTime ||
      !this.courseSlotOne.endTime ||
      !this.courseSlotOne.maxNumberOfBookings
    ) {
      return;
    }

    this.courseSlotOne.day = this.selectedCourseSlotDays.join(", ");

    this.courseService
      .addCourseSlotToCourse(this.course.courseName, this.courseSlotOne)
      .then(() => {

        this.course.courseSlots.push(this.courseSlotOne);
        this.courses.push(this.course);
        this.courses = [...this.courses];
        this.messageService.add({
          severity: "success",
          summary: "Successful",
          detail: "Course is updated successfully",
          life: 3000,
        });
        this.courseSlotOne = {};
        this.selectedCourseSlotDays = [];
      })
      .catch(() => {
        this.showError("Updating courses has failed!");
      });

    this.courseSlotsDialog = false;
    this.isCourseSlotDialogueSubmitted = false;
    this.addToCourse = false;
  }

  addCoursePaperToCourse() {
    this.isCoursePaperDialogueSubmitted = true;

    if (
      !this.coursePaper.coursePaperName ||
      !this.coursePaper.subjectName ||
      !this.coursePaper.professorName ||
      !this.coursePaper.price ||
      !this.coursePaper.academicYear ||
      !this.coursePaper.facultyName
    ) {
      return;
    }

    this.courseService
      .addCoursePaperToCourse(this.course.courseName, this.coursePaper)
      .then(() => {
        this.course.coursePapers.push(this.coursePaper);
        this.courses.push(this.course);
        this.courses = [...this.courses];
        this.messageService.add({
          severity: "success",
          summary: "Successful",
          detail: "Course is updated successfully",
          life: 3000,
        });
        this.coursePaper = {};
      })
      .catch(() => {
        this.showError("Updating courses has failed!");
      });

    this.coursePaperDialog = false;
    this.isCoursePaperDialogueSubmitted = false;
    this.addToCourse = false;
  }

  saveCourse() {
    this.isCourseSlotDialogueSubmitted = true;

    if (
      !this.selectedCoursePaperNames ||
      !this.selectedCourseSlotDays ||
      !this.courseSlotOne.startTime ||
      !this.courseSlotOne.endTime ||
      !this.courseSlotOne.maxNumberOfBookings ||
      this.courseSlotOne.endTime < this.courseSlotOne.startTime
    ) {
      return;
    }

    let selectedCoursePapers = [];

    for (let i = 0; i < this.selectedCoursePaperNames.length; i++) {
      selectedCoursePapers.push(
        this.coursePapers.find(
          (value) => value.coursePaperName == this.selectedCoursePaperNames[i]
        )
      );
    }

    this.courseSlotOne.day = this.selectedCourseSlotDays.join(", ");

    let selectedCourseSlots = [];
    selectedCourseSlots.push(this.courseSlotOne);

    this.course.coursePapers = selectedCoursePapers;
    this.course.courseSlots = selectedCourseSlots;

    this.courseService
      .createCourse(this.course)
      .then(() => {
        this.courses.push(this.course);
        this.courses = [...this.courses];
        this.messageService.add({
          severity: "success",
          summary: "Successful",
          detail: "Course is created successfully",
          life: 3000,
        });
        this.course = { coursePapers: [], courseSlots: [] };
      })
      .catch(() => {
        this.showError("Creating courses has failed!");
      });

    this.selectedCoursePaperNames = [];
    this.selectedCourseSlotDays = [];
    this.courseSlotOne = {};

    // this.courses = [...this.courses];
    this.courseSlotsDialog = false;
    this.courseDialog = false;
  }

  saveCourseWithAssignedCoursePapers() {
    this.isAssignCoursePaperDialogueSubmitted = true;

    if (
      !this.selectedCoursePaperNames
    ) {
      return;
    }

    for (let i = 0; i < this.selectedCoursePaperNames.length; i++) {

       let coursePaper = this.coursePapers.find(
          (value) => value.coursePaperName == this.selectedCoursePaperNames[i]
        )

      this.courseService
        .addCoursePaperToCourse(this.course.courseName, coursePaper)
        .then(() => {

          this.course.coursePapers.push(coursePaper);

          let coursePaperIndex = this.courses.findIndex(
            (value) => value.courseName == this.course.courseName
          )

          this.courses[coursePaperIndex] = (this.course);
          this.courses = [...this.courses];
          this.messageService.add({
            severity: "success",
            summary: "Successful",
            detail: "Course is updated successfully",
            life: 3000,
          });
          this.coursePaper = {};
        })
        .catch(() => {
          this.showError("Updating courses has failed!");
        });

    }
    this.selectedCoursePaperNames = [];
    this.assignCoursePaperDialog = false;
  }

  saveCoursePaper() {

    this.isCoursePaperDialogueSubmitted = true;

    if (
      !this.coursePaper.coursePaperName ||
      !this.coursePaper.subjectName ||
      !this.coursePaper.professorName ||
      !this.coursePaper.price ||
      !this.coursePaper.academicYear ||
      !this.coursePaper.facultyName
    ) {
      return;
    }

    let coursePaperIndex = this.coursePapers.findIndex(
      (value) => value.coursePaperName == this.coursePaper.coursePaperName
    );

    if (coursePaperIndex != -1) {
      this.coursePaperService
        .updateCoursePaper(this.coursePaper)
        .then(() => {

          for (let i = 0; i < this.coursePapers.length; i++) {
            if (
              this.coursePapers[i].coursePaperName ===
              this.coursePaper.coursePaperName
            ) {
              this.coursePapers[i] = this.coursePaper;
            }
          }
          this.coursePapers = [...this.coursePapers];
          this.messageService.add({
            severity: "success",
            summary: "Successful",
            detail: "Course Paper updated successfully",
            life: 3000,
          });
          this.coursePaper = {};
        })
        .catch(() => {
          this.showError("Updating course paper has failed!");
        });
    } else {
      this.coursePaperService
        .createCoursePaper(this.coursePaper)
        .then(() => {
          this.coursePapers.push(this.coursePaper);
          this.coursePapers = [...this.coursePapers];

          this.coursePaperNames.push(this.coursePaper.coursePaperName);
          this.coursePaperNames = [...this.coursePaperNames];

          this.messageService.add({
            severity: "success",
            summary: "Successful",
            detail: "Course Paper created successfully",
            life: 3000,
          });
          this.coursePaper = {};
        })
        .catch(() => {
          this.showError("Creating course paper has failed!");
        });
    }

    this.coursePaperDialog = false;
  }

  saveAcademicYear() {
    this.isAcademicYearDialogueSubmitted = true;
    let academicYearIndex = this.academicYears.findIndex(
      (value) => value == this.academicYearValue.getFullYear()
    );

    if (academicYearIndex != -1) {
      this.showError("Academic year already exists");
      return;
    }

    this.academicYearService
      .createAcademicYear(this.academicYearValue.getFullYear())
      .then(() => {

        let academicYear: AcademicYear = {year: this.academicYearValue.getFullYear()};
        this.academicYears.push(academicYear);
        this.academicYears = [...this.academicYears];
        this.academicYearNames.push(academicYear.year)
        this.academicYearNames = [...this.academicYearNames];
      })
      .catch(() => {
        this.showError("Saving academic year has failed!");
      });

    this.academicYearDialog = false;
  }

  saveFacultyName() {

    this.isFacultyNameDialogueSubmitted = true;
    let facultyNameIndex = this.facultyNames.findIndex(
      (value) => value == this.facultyName
    );

    if (facultyNameIndex != -1) {
      this.showError("Faculty name already exists");
      return;
    }

    this.facultyService
      .createFacultyName(this.facultyName)
      .then(() => {
        let faculty: Faculty = {name: this.facultyName};
        this.facultyNames.push(faculty);
        this.facultyNames = [...this.facultyNames];
        this.facultyNamesNames.push(faculty.name)
        this.facultyNamesNames = [...this.facultyNamesNames];
      })
      .catch(() => {
        this.showError("Saving faculty name has failed!");
      });

    this.facultyNameDialog = false;
  }

  showError(text: string) {
    this.messageService.add({
      severity: "error",
      summary: "Error",
      detail: text,
    });
  }
}
