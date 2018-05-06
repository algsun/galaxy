-- author liuzhu
-- description 添加有机污染物、无机污染物、含硫污染物,差值数据
-- date 2015-05-29

UPDATE m_sensorinfo SET sensorPrecision = 3 WHERE sensorPhysicalid IN (2057,2058,2059);

INSERT INTO `m_sensorinfo` (`sensorPhysicalid`, `escape_sensor_id`, `en_name`, `cn_name`, `sensorPrecision`, `units`, `positions`, `isActive`, `showType`, `minValue`, `maxValue`, `rangeType`, `signType`, `conditionType`, `target`, `floating`)
 VALUES('2071','0','ORGANIC_POL_DIF','有机污染物差值','2','Hz','0','1','0','0','0','0','0',NULL,NULL,NULL);
INSERT INTO `m_sensorinfo` (`sensorPhysicalid`, `escape_sensor_id`, `en_name`, `cn_name`, `sensorPrecision`, `units`, `positions`, `isActive`, `showType`, `minValue`, `maxValue`, `rangeType`, `signType`, `conditionType`, `target`, `floating`)
 VALUES('2072','0','INORGANIC_POL_DIF','无机污染物差值','2','Hz','0','1','0','0','0','0','0',NULL,NULL,NULL);
INSERT INTO `m_sensorinfo` (`sensorPhysicalid`, `escape_sensor_id`, `en_name`, `cn_name`, `sensorPrecision`, `units`, `positions`, `isActive`, `showType`, `minValue`, `maxValue`, `rangeType`, `signType`, `conditionType`, `target`, `floating`)
 VALUES('2073','0','SULFUROUS_POL_DIF','含硫污染物差值','2','Hz','0','1','0','0','0','0','0',NULL,NULL,NULL);
 
 
 
 INSERT INTO `m_formula_sensor` (`device_id`, `sensor_id`, `formula_id`, `min_x`, `max_x`, `x_range_type`, `min_y`, `max_y`, `y_range_type`, `sign_type`)
 VALUES('0','2071','1','0','0','0','0','0','0','0');
INSERT INTO `m_formula_sensor` (`device_id`, `sensor_id`, `formula_id`, `min_x`, `max_x`, `x_range_type`, `min_y`, `max_y`, `y_range_type`, `sign_type`)
 VALUES('0','2072','1','0','0','0','0','0','0','0');
INSERT INTO `m_formula_sensor` (`device_id`, `sensor_id`, `formula_id`, `min_x`, `max_x`, `x_range_type`, `min_y`, `max_y`, `y_range_type`, `sign_type`)
 VALUES('0','2073','1','0','0','0','0','0','0','0');

INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','2071','a','1');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','2071','b','0');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','2072','a','1');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','2072','b','0');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','2073','a','1');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','2073','b','0');