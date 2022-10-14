DROP TABLE IF EXISTS `academic_year`;

CREATE TABLE `academic_year` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `year`              varchar(255)   not null,

                          UNIQUE KEY `unique_year` (`year`),
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `faculty`;

CREATE TABLE `faculty` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `name`            varchar(255)   not null,

                           UNIQUE KEY `unique_name` (`name`),
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB;