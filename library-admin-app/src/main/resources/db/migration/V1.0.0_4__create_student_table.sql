DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `student_name`      varchar(255)   not null,
                          `mobile_number`     varchar(255)   not null,
                          `faculty_name`      varchar(255)   not null,
                          `academic_year`     varchar(255)   not null,
                          `status`            varchar(255)   not null,

                          UNIQUE KEY `unique_student_name` (`student_name`),
                          UNIQUE KEY `unique_student_mobile` (`mobile_number`),
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `student_course_slots` (
                           `student_id`      bigint not null,
                           `course_slots_id`      bigint not null,

                           UNIQUE KEY `unique_student_course` (`student_id`, `course_slots_id`),
                           foreign key (student_id) references student(id),
                           foreign key (course_slots_id) references course_slot(id)
) ENGINE=InnoDB;

CREATE TABLE `student_course_papers` (
                         `student_id`            bigint,
                         `course_papers_id`      bigint,

                         UNIQUE KEY `unique_student_course_papers` (`student_id`, `course_papers_id`),
                         foreign key (student_id) references student(id),
                         foreign key (course_papers_id) references course_paper(id)
) ENGINE=InnoDB;