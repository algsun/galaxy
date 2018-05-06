--author 
--description 站点地图定制

DROP TABLE IF EXISTS b_map_customize;
CREATE TABLE `b_map_customize` (
  `id` INT (11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `siteId` VARCHAR (50) NOT NULL COMMENT '站点Id',
  `deviceId` VARCHAR(50) NOT NULL COMMENT '设备Id',
  `sensorId` INT (11) NOT NULL COMMENT '监测指标Id',
  `customizeRemark` VARCHAR(400) COMMENT '定制备注',
  PRIMARY KEY (id),
  FOREIGN KEY (siteId) REFERENCES t_logicgroup(siteId),
  FOREIGN KEY (sensorId) REFERENCES m_sensorinfo(sensorPhysicalid),
  UNIQUE KEY `UK_b_map_customize_logicGroupId_sensorId` (`siteId`,`sensorId`,`deviceId`) 
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '站点地图监测指标定制';	