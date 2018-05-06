--author xiedeng
--description 添加人员停留时间表
CREATE TABLE IF NOT EXISTS `u_user_stay_times` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `zoneId` VARCHAR(50) NOT NULL COMMENT '区域编号',
  `stayTimes` BIGINT(20) NOT NULL COMMENT '停留时间(毫秒)',
  `createTime` DATETIME NOT NULL COMMENT '统计时间',
  PRIMARY KEY (`id`),
  KEY `createTime` (`createTime`)
) ENGINE=INNODB DEFAULT CHARSET=utf8
