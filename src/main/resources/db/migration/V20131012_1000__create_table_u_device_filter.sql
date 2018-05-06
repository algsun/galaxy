--author xu.baoji
--description 添加设备过滤白名单 记录表
CREATE TABLE `u_device_filter` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `siteId` VARCHAR(50) NOT NULL COMMENT '站点id',
  `sn`   VARCHAR(10) NOT NULL COMMENT '设备sn',
  `type` INT(1) NOT NULL DEFAULT '2' COMMENT '设备类型 1 读卡器，2 激发器 3 电子卡',
  PRIMARY KEY (`id`),
  UNIQUE KEY `siteId` (`siteId`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='设备过滤白名单'