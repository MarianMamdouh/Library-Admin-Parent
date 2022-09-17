DROP TABLE IF EXISTS `professor`;

CREATE TABLE `professor` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) not null,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;
