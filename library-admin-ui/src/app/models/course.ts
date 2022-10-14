import {CoursePaper} from "./coursePaper";
import {CourseSlot} from "./courseSlot";

export interface Course {

  id?:number
  courseName?:string;
  subjectName?:string;
  professorName?:string;
  pricePerSemester?:number;
  pricePerMonth?:number;
  academicYear?:number;
  facultyName?:string;
  coursePapers: CoursePaper[];
  courseSlots: CourseSlot[];
}
