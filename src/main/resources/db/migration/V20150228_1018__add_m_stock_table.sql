--author liuzhu
--description 添加m_stock表

CREATE TABLE `m_stock` (
  `id` varchar(50) NOT NULL COMMENT '自增id',
  `locationId` varchar(20) NOT NULL COMMENT '位置点id',
  `sensorId` int(11) NOT NULL COMMENT '监测指标id',
  `beginValue` double DEFAULT NULL COMMENT '当天开始值',
  `endValue` double DEFAULT NULL COMMENT '当天结束值',
  `maxValue` double DEFAULT NULL COMMENT '当天最大值',
  `minValue` double DEFAULT NULL COMMENT '当天最小值',
  `k` float DEFAULT NULL COMMENT 'k值',
  `j` float DEFAULT NULL COMMENT 'j值',
  `d` float DEFAULT NULL COMMENT 'd值',
  `avgValue5` float DEFAULT NULL COMMENT '5日均线',
  `avgValue10` float DEFAULT NULL COMMENT '10日均线',
  `avgValue30` float DEFAULT NULL COMMENT '30日均线',
  `stamp` date DEFAULT NULL COMMENT '时间',
  `dataVersion` bigint(20) DEFAULT '0' COMMENT '数据同步版本',
  PRIMARY KEY (`id`),
  KEY `stamp` (`stamp`),
  KEY `FK_m_location_id` (`locationId`),
  KEY `FK_m_sensorinfo_sensorId` (`sensorId`),
  CONSTRAINT `FK_m_location_id` FOREIGN KEY (`locationId`) REFERENCES `m_location` (`id`),
  CONSTRAINT `FK_m_sensorinfo_sensorId` FOREIGN KEY (`sensorId`) REFERENCES `m_sensorinfo` (`sensorPhysicalid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='位置点kdj表'

