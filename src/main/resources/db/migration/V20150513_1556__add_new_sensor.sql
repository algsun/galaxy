-- author liuzhu
-- description 添加有机污染物、无机污染物、含硫污染物
-- date 2015-5-7

INSERT INTO `m_sensorinfo` (`sensorPhysicalid`, `escape_sensor_id`, `en_name`, `cn_name`, `sensorPrecision`, `units`, `positions`, `isActive`, `showType`, `minValue`, `maxValue`, `rangeType`, `signType`, `conditionType`, `target`, `floating`)
 VALUES('2057','0','ORGANIC_POL','有机污染物','1','Hz','0','1','0','0','0','0','0',NULL,NULL,NULL);
INSERT INTO `m_sensorinfo` (`sensorPhysicalid`, `escape_sensor_id`, `en_name`, `cn_name`, `sensorPrecision`, `units`, `positions`, `isActive`, `showType`, `minValue`, `maxValue`, `rangeType`, `signType`, `conditionType`, `target`, `floating`)
 VALUES('2058','0','INORGANIC_POL','无机污染物','1','Hz','0','1','0','0','0','0','0',NULL,NULL,NULL);
INSERT INTO `m_sensorinfo` (`sensorPhysicalid`, `escape_sensor_id`, `en_name`, `cn_name`, `sensorPrecision`, `units`, `positions`, `isActive`, `showType`, `minValue`, `maxValue`, `rangeType`, `signType`, `conditionType`, `target`, `floating`)
 VALUES('2059','0','SULFUROUS_POL','含硫污染物','1','Hz','0','1','0','0','0','0','0',NULL,NULL,NULL);

INSERT INTO `m_formula_sensor` (`device_id`, `sensor_id`, `formula_id`, `min_x`, `max_x`, `x_range_type`, `min_y`, `max_y`, `y_range_type`, `sign_type`)
 VALUES('0','2057','1','0','0','0','0','0','0','0');
INSERT INTO `m_formula_sensor` (`device_id`, `sensor_id`, `formula_id`, `min_x`, `max_x`, `x_range_type`, `min_y`, `max_y`, `y_range_type`, `sign_type`)
 VALUES('0','2058','1','0','0','0','0','0','0','0');
INSERT INTO `m_formula_sensor` (`device_id`, `sensor_id`, `formula_id`, `min_x`, `max_x`, `x_range_type`, `min_y`, `max_y`, `y_range_type`, `sign_type`)
 VALUES('0','2059','1','0','0','0','0','0','0','0');

INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','2057','a','1');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','2057','b','0');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','2058','a','1');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','2058','b','0');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','2059','a','1');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','2059','b','0');