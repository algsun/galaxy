--author xu.baoji
--description 添加 人员监听端口 与  站点对应关系
CREATE TABLE `u_port_site_mapping` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `port` INT(11) NOT NULL COMMENT '服务监听端口',
  `siteId` VARCHAR(50) NOT NULL COMMENT '站点id',
  `isFilterExciter` INT(1) NOT NULL DEFAULT '0' COMMENT '是否过滤激发器 0 不过滤，1过滤',
  PRIMARY KEY (`id`),
  UNIQUE KEY `port` (`port`),
  UNIQUE KEY `siteId` (`siteId`)
) ENGINE=INNODB DEFAULT CHARSET=utf8