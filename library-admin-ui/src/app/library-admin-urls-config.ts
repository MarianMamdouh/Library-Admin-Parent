export class LibraryAdminUrlsConfig {
    public static get ENVIROMENT(): string {
        return "http://localhost:8080/";
    }

  public static get PRODUCTION(): string {
    return "http://ec2-34-238-40-251.compute-1.amazonaws.com:8080/";
  }

    public static get COURSES_URL(): string {
        return this.ENVIROMENT + "courses";
    }

    public static get COURSE_PAPERS_URL(): string {
        return this.ENVIROMENT + "coursePapers";
    }


  public static get ACADEMIC_YEAR_URL(): string {
    return this.ENVIROMENT + "academicYear";
  }

  public static get FACULTY_NAME_URL(): string {
    return this.ENVIROMENT + "faculty";
  }

  public static get STUDENT_URL(): string {
    return this.ENVIROMENT + "students";
  }
}
