DROP TABLE IF EXISTS `course_paper`;

CREATE TABLE `course_paper` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `course_paper_name`      varchar(255)   not null,
                          `subject_name`           varchar(255)   not null,
                          `professor_name`         varchar(255)   not null,
                          `price`                  decimal(19, 2) not null,
                          `academic_year_id`          bigint      not null,
                          `faculty_id`                bigint      not null,

                          UNIQUE KEY `unique_course_paper_name` (`course_paper_name`),
                          foreign key (academic_year_id) references academic_year(id),
                          foreign key (faculty_id) references faculty(id),
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `course_course_papers`;

CREATE TABLE `course_course_papers` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `course_papers_id`     bigint   not null,
                                `course_id`           bigint   not null,

                                foreign key (course_papers_id) references course_paper(id),
                                foreign key (course_id) references course(id),
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB;