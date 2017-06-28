-- local mysql start
-- mysqld --defaults-file=C:\lichunhui\mysql-5.6.36-winx64\my.ini --console
-- create database crawler;
-- use crawler;
Drop table `crawler`.`crawler_job`;
CREATE TABLE `crawler_job` (
	`crawler_job_id` INT(11) NOT NULL,
	`crawler_job_type` VARCHAR(1) NOT NULL COLLATE 'utf8_unicode_ci',
	`crawler_search_url` VARCHAR(255) NOT NULL COLLATE 'utf8_unicode_ci',
	`crawler_flag` VARCHAR(1) NOT NULL DEFAULT '0' COLLATE 'utf8_unicode_ci',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`create_by` VARCHAR(45) NOT NULL DEFAULT '-1' COLLATE 'utf8_unicode_ci',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`update_by` VARCHAR(45) NOT NULL DEFAULT '-1' COLLATE 'utf8_unicode_ci',
	PRIMARY KEY (`crawler_job_id`)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;

  
Drop table `crawler_product_job`;
CREATE TABLE `crawler_product_job` (
	`crawler_product_no` VARCHAR(255) NOT NULL COLLATE 'utf8_unicode_ci',
	`crawler_product_flag` VARCHAR(1) NOT NULL DEFAULT '0' COLLATE 'utf8_unicode_ci',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`create_by` VARCHAR(45) NOT NULL DEFAULT '-1' COLLATE 'utf8_unicode_ci',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`update_by` VARCHAR(45) NOT NULL DEFAULT '-1' COLLATE 'utf8_unicode_ci',
	PRIMARY KEY (`crawler_product_no`)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;

