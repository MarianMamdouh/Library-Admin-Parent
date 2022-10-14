DROP TABLE IF EXISTS `course_paper`;

CREATE TABLE `course_paper` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `course_paper_name`      varchar(255)   not null,
                          `subject_name`           varchar(255)   not null,
                          `professor_name`         varchar(255)   not null,
                          `price`                  decimal(19, 2) not null,
                          `course_id`              bigint         null,
                          `academic_year_id`          bigint      not null,
                          `faculty_id`                bigint      not null,

                          UNIQUE KEY `unique_course_paper_name` (`course_paper_name`),
                          foreign key (academic_year_id) references academic_year(id),
                          foreign key (faculty_id) references faculty(id),
                          foreign key (course_id) references course(id),
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB;