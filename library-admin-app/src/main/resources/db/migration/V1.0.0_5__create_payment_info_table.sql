DROP TABLE IF EXISTS `payment_info`;

CREATE TABLE `payment_info` (
                          `id`                  bigint NOT NULL AUTO_INCREMENT,
                          `payment_number`      bigint not null,
                          `student_id`          bigint not null,
                          `course_paper_id`     bigint,
                          `course_id`           bigint,
                          `delivery_address`    varchar(255),

                          UNIQUE KEY `payment_number` (`payment_number`),
                          foreign key (student_id) references student(id),
                          foreign key (course_paper_id) references course_paper(id),
                          foreign key (course_id) references course(id),
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB;