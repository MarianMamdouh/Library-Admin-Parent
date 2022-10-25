DROP TABLE IF EXISTS `student_notification`;

CREATE TABLE `student_notification` (
                                `id`                  bigint NOT NULL AUTO_INCREMENT,
                                `student_id`          bigint not null,
                                `notification`    varchar(255),

                                foreign key (student_id) references student(id),
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB;