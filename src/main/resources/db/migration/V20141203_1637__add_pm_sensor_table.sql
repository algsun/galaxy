-- 添加表 m_pm_sensor_data
CREATE TABLE IF NOT EXISTS `m_pm_sensor_data` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nodeid` VARCHAR(20) NOT NULL COMMENT '产品入网唯一标识',
  `sensorPhysicalid` INT(11) NOT NULL COMMENT '传感标识',
  `sensorPhysicalvalue` VARCHAR(30) NOT NULL COMMENT '传感值',
  `lowvoltage` FLOAT NOT NULL DEFAULT '0' COMMENT '电压值(默认为0)',
  `createtime` DATETIME NOT NULL COMMENT '创建时间',
  `state` INT(11) NOT NULL COMMENT '传感状态0：采样失败,1：采样正常,2：低于低阀值,3：超过高阀值,4：空数据',
  `dataVersion` INT(11) DEFAULT '0' COMMENT '数据版本',
  `anomaly` INT(11) NOT NULL DEFAULT '0' COMMENT '-1：超时,0：正常,1：低压,2：掉电',
  PRIMARY KEY (`id`),
  KEY `createtime` (`createtime`)
) ENGINE=INNODB AUTO_INCREMENT=165 DEFAULT CHARSET=utf8 COMMENT='pm传感数据表'