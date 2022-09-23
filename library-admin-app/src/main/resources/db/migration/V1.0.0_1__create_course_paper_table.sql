DROP TABLE IF EXISTS `course_paper`;

CREATE TABLE `course_paper` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `course_paper_name`      varchar(255)   not null,
                          `subject_name`           varchar(255)   not null,
                          `professor_name`         varchar(255)   not null,
                          `price`                  decimal(19, 2) not null,
                          `num_of_copies`          int            null,
                          `course_id`              bigint         not null,

                          UNIQUE KEY `unique_course_paper_name` (`course_paper_name`),
                          PRIMARY KEY (`id`),
                          foreign key (course_id) references course(id)
) ENGINE=InnoDB;