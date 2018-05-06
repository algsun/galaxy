-- author chen.yaofei
-- description 添加m_zone_avgdata表

CREATE TABLE IF NOT EXISTS `m_zone_avgdata` (
  `id` VARCHAR(50) NOT NULL COMMENT '唯一标识UUID',
  `zoneId` VARCHAR(50) NOT NULL COMMENT '区域ID',
  `sensorId` INT(11) NOT NULL COMMENT '监测指标ID',
  `avgValue` DOUBLE DEFAULT NULL COMMENT '平均值',
  `ms_date` DATE DEFAULT NULL COMMENT '日期',
  PRIMARY KEY (`id`),
  KEY `ms_date` (`ms_date`),
  KEY `FK_T_ZONE_ZONE_ID` (`zoneId`),
  KEY `FK_M_SENSORINFO_SENSOR_PHYSICAL_ID` (`sensorId`),
  CONSTRAINT `FK_T_ZONE_ZONE_ID` FOREIGN KEY (`zoneId`) REFERENCES `t_zone` (`zoneId`),
  CONSTRAINT `FK_M_SENSORINFO_SENSOR_PHYSICAL_ID` FOREIGN KEY (`sensorId`) REFERENCES `m_sensorinfo` (`sensorPhysicalid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='各区域不同监测指标平均值';