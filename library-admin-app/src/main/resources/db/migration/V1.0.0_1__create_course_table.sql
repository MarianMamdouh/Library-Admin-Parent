DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `course_name`            varchar(255)   not null,
                          `subject_name`           varchar(255)   not null,
                          `professor_name`         varchar(255)   not null,
                          `price_per_month`        decimal(19, 2) not null,
                          `price_per_semester`     decimal(19, 2) not null,

                          `academic_year_id`          bigint      not null,
                          `faculty_id`                bigint      not null,

                          UNIQUE KEY `unique_course_name` (`course_name`),
                          foreign key (academic_year_id) references academic_year(id),
                          foreign key (faculty_id) references faculty(id),

                          PRIMARY KEY (`id`)
                      ) ENGINE=InnoDB;