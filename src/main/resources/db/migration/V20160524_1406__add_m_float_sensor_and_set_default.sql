-- author : chenyaofei
-- date 2016-05-24
-- description add  create table `m_float_sensor` and set default

DROP TABLE IF EXISTS `m_float_sensor`;
CREATE TABLE `m_float_sensor` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `device_id` VARCHAR(13) NOT NULL COMMENT '设备id',
  `sensor_id` INT(11) NOT NULL COMMENT '监测指标id',
  `max_up_float` DOUBLE DEFAULT 0 COMMENT '上限浮动值向上浮动',
  `min_down_float` DOUBLE DEFAULT 0 COMMENT '下限浮动值向下浮动',
  `min_up_float` DOUBLE DEFAULT 0 COMMENT '下限浮动值向上浮动',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '设备监测指标上下限浮动值';

INSERT INTO m_float_sensor(device_id,sensor_id)
SELECT '0',sensorPhysicalid FROM m_sensorinfo;