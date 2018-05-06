--王耕
--文物保存现状扩展四个字段满足文物修复系统
ALTER TABLE `o_status_quo` ADD `conserve` VARCHAR(200) NULL COMMENT '文物保存环境';
ALTER TABLE `o_status_quo` ADD `repairCases` VARCHAR(1000) NULL COMMENT '历次保护修复情况';
ALTER TABLE `o_status_quo` ADD `remark` VARCHAR(200) NULL COMMENT '备注';

ALTER TABLE `o_status_quo` CHANGE COLUMN quoDate quoDate DATETIME;