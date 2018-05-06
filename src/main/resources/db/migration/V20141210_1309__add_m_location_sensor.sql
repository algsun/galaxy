--添加 m_locationsensor
--李建飞

CREATE TABLE IF NOT EXISTS `m_location_sensor` (
  `id` VARCHAR(50) NOT NULL COMMENT 'uuid',
  `locationId` VARCHAR(20) NOT NULL COMMENT '位置点唯一标识',
  `sensorPhysicalId` INT(11) NOT NULL COMMENT '传感量标识',
  `sensorPhysicalValue` VARCHAR(30) NOT NULL COMMENT '传感量值',
  `state` INT(11) NOT NULL DEFAULT '1' COMMENT '0：采样失败  0xFFFF为采样失败\n            1：采样正常\n            2：低于低阈值\n            3：超过高阈值\n            4：空数据（前台暂不处理）',
  `stamp` DATETIME NOT NULL COMMENT '数据采样时间戳（实时数据显示时采用一组数据中时间最大值）',
  `dataVersion` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '数据同步版本',
  PRIMARY KEY (`id`),
  UNIQUE KEY `locationId` (`locationId`,`sensorPhysicalId`),
  KEY `FK_location_locationsensor` (`locationId`),
  CONSTRAINT `FK_location_locationsensor` FOREIGN KEY (`locationId`) REFERENCES `m_location` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='位置点实时数据表';

