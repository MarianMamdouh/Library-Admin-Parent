DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `course_name`            varchar(255)   not null,
    `max_number_of_bookings` int            not null,
    `price_per_month`        decimal(19, 2) not null,
    `price_per_semester`     decimal(19, 2) not null,
    `subject_name`           varchar(255)   not null,
    `professor_id`           bigint         not null,
    PRIMARY KEY (`id`),
    foreign key (professor_id) references professor(id)
    ) ENGINE=InnoDB;