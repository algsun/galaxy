-- author : chenyaofei
-- date 2016-05-20
-- description add  create table `m_float_sensor` and set default

DROP TABLE IF EXISTS `m_float_sensor`;
CREATE TABLE `m_float_sensor` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `device_id` VARCHAR(13) NOT NULL COMMENT '设备id',
  `sensor_id` INT(11) NOT NULL COMMENT '监测指标id',
  `float_min_y` DOUBLE DEFAULT 0 COMMENT '浮动值下限',
  `float_max_y` DOUBLE DEFAULT 0 COMMENT '浮动值上限',
  `zero_float_min_y` DOUBLE DEFAULT 0 COMMENT '下限零值向上浮动值',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '设备监测指标上下限浮动值';

INSERT INTO m_float_sensor(device_id,sensor_id)
SELECT '0',sensorPhysicalid FROM m_sensorinfo;