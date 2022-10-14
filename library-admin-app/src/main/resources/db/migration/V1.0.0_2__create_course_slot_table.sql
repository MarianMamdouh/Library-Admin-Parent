DROP TABLE IF EXISTS `course_slot`;

CREATE TABLE `course_slot` (
                          `id`                         bigint NOT NULL AUTO_INCREMENT,
                          `day`                        varchar(255)   not null,
                          `start_time`                 datetime   not null,
                          `end_time`                   datetime   not null,
                          `maximum_number_of_bookings` int            not null,
                          `current_number_of_bookings` int        null,
                          `course_id`                  bigint     not null,

                          PRIMARY KEY (`id`),
                          foreign key (course_id) references course(id)
) ENGINE=InnoDB;