DROP TABLE IF EXISTS `USER_MASTER`;
CREATE TABLE `USER_MASTER` (
  `id` INT(6) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(90) NOT NULL,
  `account` VARCHAR(90) NOT NULL,
  `password` VARCHAR(36) NOT NULL,
  `del_fg` CHAR(1) NOT NULL DEFAULT '0',
  `create_time` TIMESTAMP(6) NOT NULL DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) NOT NULL DEFAULT now(),
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  PRIMARY KEY (`id`)
)
;


CREATE TABLE `auth_master` (
  `id` INT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(90) NOT NULL,
  `del_fg` CHAR(1) NOT NULL DEFAULT '0',
  `create_time` TIMESTAMP(6) NOT NULL DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) NOT NULL DEFAULT now() ,
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  PRIMARY KEY (`id`)
)
;

DROP TABLE IF EXISTS `user_auth`;
CREATE TABLE `user_auth` (
  `user_id` INT(6) UNSIGNED NOT NULL,
  `auth_id` VARCHAR(3) NOT NULL,
  `del_fg` CHAR(1) NOT NULL DEFAULT '0',
  `create_time` TIMESTAMP(6) NOT NULL DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) NOT NULL DEFAULT now() ,
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  PRIMARY KEY (`user_id`, `auth_id`)
)
;

DROP TABLE IF EXISTS `product_master`;
CREATE TABLE `product_master` (
  `jan_cd` DECIMAL(17) NOT NULL,
  `product_name` VARCHAR(255) NOT NULL,
  `product_name_kn` VARCHAR(40),
  `drug_fg` CHAR(1) NOT NULL DEFAULT '0',
  `drug_risk_type` CHAR(2),
  `del_fg` CHAR(1) NOT NULL DEFAULT '0',
  `create_time` TIMESTAMP(6) NOT NULL DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) NOT NULL DEFAULT now() ,
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  PRIMARY KEY (`jan_cd`)
)
;

DROP TABLE IF EXISTS `warehouse_master`;
CREATE TABLE `warehouse_master` (
  `warehouse_cd` VARCHAR(50) NOT NULL,
  `warehouse_name` VARCHAR(150) NOT NULL,
  `master_warehouse_fg` CHAR(1) NOT NULL DEFAULT '0',
  `shipper_limited_flg` CHAR(1) NOT NULL DEFAULT '0',
  `shipper_limited_qty` INT(5) DEFAULT '0',
  `torc_warehouse_fg` CHAR(1) DEFAULT '0',
  `payment_kb` CHAR(1),
  `shipper_buffer_days` INT(2),
  `create_time` TIMESTAMP(6) NOT NULL DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) NOT NULL DEFAULT now() ,
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  PRIMARY KEY (`warehouse_cd`)
)
;

DROP TABLE IF EXISTS `product_warehouse_attachment_master`;
CREATE TABLE `product_warehouse_attachment_master` (
  `jan_cd` DECIMAL(17) NOT NULL,
  `warehouse_cd` VARCHAR(50) NOT NULL,
  `warehouse_priority` INT(2) NOT NULL,
  `warehouse_handling_fg` CHAR(1) NOT NULL DEFAULT '0',
  `stock_mortgage_stop_fg` CHAR(1) NOT NULL DEFAULT '0',
  `soukai_available_fg` CHAR(1) NOT NULL DEFAULT '0',
  `torc_available_fg` CHAR(1) NOT NULL DEFAULT '0',
  `create_time` TIMESTAMP(6) DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) DEFAULT now() ,
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  PRIMARY KEY (`jan_cd`, `warehouse_cd`)
)
;

DROP TABLE IF EXISTS `zip_warehouse_attachment_master`;
CREATE TABLE `zip_warehouse_attachment_master` (
  `zip_cd` VARCHAR(7) NOT NULL,
  `warehouse_cd` VARCHAR(50) NOT NULL,
  `priority_rank` INT(2) NOT NULL DEFAULT 1,
  `create_time` TIMESTAMP(6) NOT NULL DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) NOT NULL DEFAULT now() ,
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  PRIMARY KEY (`zip_cd`, `warehouse_cd`)
)
;

DROP TABLE IF EXISTS `shipping_time`;
CREATE TABLE `shipping_time` (
  `delivery_company_cd` DECIMAL(24) NOT NULL,
  `shipping_warehouse_cd` VARCHAR(50) NOT NULL,
  `delivery_zipcode` VARCHAR(7) NOT NULL,
  `delivery_leadtime` INT(2),
  `create_time` TIMESTAMP(6) NOT NULL DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) NOT NULL DEFAULT now() ,
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  `del_fg` CHAR(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`delivery_company_cd`, `shipping_warehouse_cd`, `delivery_zipcode`)
)
;

DROP TABLE IF EXISTS `order_header`;
CREATE TABLE `order_header` (
  `wts_order_no` DECIMAL(17) NOT NULL,
  `order_ymd` DATETIME NOT NULL,
  `order_status` VARCHAR(2) NOT NULL DEFAULT '00',
  `payment_kb` CHAR(1),
  `cancel_fg` CHAR(1) NOT NULL DEFAULT '0',
  `receiver_zip_cd` VARCHAR(7),
  `priority_rank` INT(2),
  `delivery_reserve_date` DATE,
  `delivery_day` DATE,
  `stock_mortgage_check_fg` CHAR(1) NOT NULL DEFAULT '0',
  `mortgage_target_fg` CHAR(1) NOT NULL DEFAULT '0',
  `create_time` TIMESTAMP(6) NOT NULL DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) NOT NULL DEFAULT now() ,
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  PRIMARY KEY (`wts_order_no`, `order_ymd`)
)
;

DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `wts_order_no` DECIMAL(17) NOT NULL,
  `order_datetime` DATETIME NOT NULL,
  `detail_no` INT(3) NOT NULL,
  `jan_cd` DECIMAL(17) NOT NULL,
  `warehouse_cd` VARCHAR(50) NOT NULL,
  `demanding_count` INT(5) NOT NULL DEFAULT 0,
  `allocated_count` INT(5) NOT NULL DEFAULT 0,
  `correct_pending_count` INT(5) NOT NULL DEFAULT 0,
  `temp_pending_count` INT(5) NOT NULL DEFAULT 0,
  `committed_delivery_date` DATE,
  `uncommitted_delivery_date` DATE,
  `shipment_date` DATE,
  `delivery_leadtime` INT(2),
  `default_deliverry_date` INT(2),
  `deliverry_date_status` CHAR(1),
  `create_time` TIMESTAMP(6) NOT NULL DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) NOT NULL DEFAULT now() ,
  PRIMARY KEY (`wts_order_no`, `order_datetime`, `detail_no`)
)
;

DROP TABLE IF EXISTS `order_detail_for_burst_check`;
CREATE TABLE `order_detail_for_burst_check` (
  `wts_order_no` DECIMAL(17) NOT NULL,
  `detail_no` VARCHAR(255) NOT NULL,
  `order_ymd` VARCHAR(40) NOT NULL,
  `jan_cd` VARCHAR(255) NOT NULL,
  `product_qty` VARCHAR(255) NOT NULL,
  `create_time` TIMESTAMP(6) NOT NULL DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) NOT NULL DEFAULT now() ,
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  PRIMARY KEY (`wts_order_no`, `detail_no`, `order_ymd`)
)
;

DROP TABLE IF EXISTS `burst_control`;
CREATE TABLE `burst_control` (
  `jan_cd` DECIMAL(17) NOT NULL,
  `busrt_start_time` DATE NOT NULL,
  `create_time` TIMESTAMP(6) NOT NULL DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) NOT NULL DEFAULT now() ,
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  PRIMARY KEY (`jan_cd`)
)
;

DROP TABLE IF EXISTS `core_stock`;
CREATE TABLE `core_stock` (
  `warehouse_cd` VARCHAR(50) NOT NULL,
  `jan_cd` DECIMAL(17) NOT NULL,
  `superior_count` INT(5) NOT NULL DEFAULT 0,
  `mortgage_possible_count` INT(5) NOT NULL DEFAULT 0,
  `correct_pending_count` INT(5) NOT NULL DEFAULT 0,
  `temp_pending_count` INT(5) NOT NULL DEFAULT 0,
  `create_time` TIMESTAMP(6) NOT NULL DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) NOT NULL DEFAULT now() ,
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  PRIMARY KEY (`warehouse_cd`, `jan_cd`)
)
;

DROP TABLE IF EXISTS `arrives_warehouse`;
CREATE TABLE `arrives_warehouse` (
  `id` BIGINT(12) NOT NULL AUTO_INCREMENT,
  `arrive_date` DATE NOT NULL,
  `warehouse_cd` VARCHAR(50) NOT NULL,
  `jan_cd` VARCHAR(255) NOT NULL,
  `arrives_warehouse_superior_count` INT(5) NOT NULL DEFAULT 0,
  `target_fg` CHAR(1) NOT NULL DEFAULT '0',
  `create_time` TIMESTAMP(6) NOT NULL DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) NOT NULL DEFAULT now() ,
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  PRIMARY KEY (`id`)
)
;

DROP TABLE IF EXISTS `takes_warehouse`;
CREATE TABLE `takes_warehouse` (
  `id` BIGINT(12) NOT NULL AUTO_INCREMENT,
  `takes_warehouse_date` DATE NOT NULL,
  `takes_warehouse_kb` VARCHAR(255) NOT NULL DEFAULT '0',
  `warehouse_cd` VARCHAR(50) NOT NULL,
  `jan_cd` DECIMAL(17) NOT NULL,
  `takes_warehouse_superior_count` INT(5) NOT NULL DEFAULT 0,
  `wms_takes_warehouse_slip_no` BIGINT(12),
  `wms_takes_warehouse_detail_no` BIGINT(12) NOT NULL,
  `target_fg` CHAR(1) NOT NULL DEFAULT '0',
  `create_time` TIMESTAMP(6) NOT NULL DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) NOT NULL DEFAULT now() ,
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  PRIMARY KEY (`id`, `takes_warehouse_date`)
)
;

DROP TABLE IF EXISTS `shipments_warehouse`;
CREATE TABLE `shipments_warehouse` (
  `id` BIGINT(12) NOT NULL AUTO_INCREMENT,
  `shipment_date` DATE NOT NULL,
  `wts_order_no` DECIMAL(17) NOT NULL,
  `warehouse_cd` VARCHAR(50) NOT NULL,
  `jan_cd` VARCHAR(255) NOT NULL,
  `shipment_count` INT(5) NOT NULL DEFAULT 0,
  `shipment_complete_status` CHAR(1) NOT NULL DEFAULT '0',
  `again_shipment_fg` CHAR(1) NOT NULL DEFAULT '0',
  `again_shipment_cancel_fg` CHAR(1) NOT NULL DEFAULT '0',
  `target_fg` CHAR(1) NOT NULL DEFAULT '0',
  `create_time` TIMESTAMP(6) NOT NULL DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) NOT NULL DEFAULT now() ,
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  PRIMARY KEY (`id`)
)
;

DROP TABLE IF EXISTS `web_stock`;
CREATE TABLE `web_stock` (
  `jan_cd` DECIMAL(17) NOT NULL,
  `web_stock_count` INT(5) NOT NULL DEFAULT 0,
  `web_stock_count_set_fg` CHAR(1) NOT NULL DEFAULT 0,
  `no_mortgage_upper_limit_count` INT(5),
  `create_time` TIMESTAMP(6) NOT NULL DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) NOT NULL DEFAULT now() ,
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  PRIMARY KEY (`jan_cd`)
)
;

DROP TABLE IF EXISTS `product_master_tmp`;
CREATE TABLE `product_master_tmp` (
  `jan_cd` DECIMAL(17) NOT NULL,
  `product_name` VARCHAR(255) NOT NULL,
  `product_name_kn` VARCHAR(40),
  `drug_fg` CHAR(1) NOT NULL DEFAULT '0',
  `drug_risk_type` CHAR(2),
  `create_time` TIMESTAMP(6) DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) DEFAULT now() ,
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  PRIMARY KEY (`jan_cd`)
)
;

DROP TABLE IF EXISTS `product_warehouse_attachment_master_tmp`;
CREATE TABLE `product_warehouse_attachment_master_tmp` (
  `jan_cd` DECIMAL(17) NOT NULL,
  `warehouse_cd` VARCHAR(50) NOT NULL,
  `warehouse_priority` INT(2) NOT NULL,
  `warehouse_handling_fg` CHAR(1) NOT NULL DEFAULT '0',
  `stock_mortgage_stop_fg` CHAR(1) NOT NULL DEFAULT '0',
  `soukai_available_fg` CHAR(1) NOT NULL DEFAULT '0',
  `torc_available_fg` CHAR(1) NOT NULL DEFAULT '0',
  `create_time` TIMESTAMP(6) DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) DEFAULT now() ,
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  PRIMARY KEY (`jan_cd`, `warehouse_cd`)
)
;

DROP TABLE IF EXISTS `journal_tmp`;
CREATE TABLE `journal_tmp` (
  `jan_cd` DECIMAL(17) NOT NULL,
  `warehouse_cd` VARCHAR(50) NOT NULL,
  `warehouse_priority` INT(2) NOT NULL,
  PRIMARY KEY (`jan_cd`, `warehouse_cd`)
)
;

DROP TABLE IF EXISTS `deemed_date_management`;
CREATE TABLE `deemed_date_management` (
  `current_deemed_date` DATE NOT NULL,
  `day_process_flg` CHAR(1) NOT NULL,
  `create_time` TIMESTAMP(6) NOT NULL DEFAULT now(),
  `create_user_id` VARCHAR(6),
  `create_pgm` VARCHAR(50),
  `update_ime` TIMESTAMP(6) NOT NULL DEFAULT now() ,
  `update_user_id` VARCHAR(6),
  `update_pgm` VARCHAR(50),
  PRIMARY KEY (`current_deemed_date`)
)
;

