-- author 刘柱
-- description 添加qcm监测指标(double双精度)
-- date 2015-7-1

INSERT INTO `m_sensorinfo` (`sensorPhysicalid`, `escape_sensor_id`, `en_name`, `cn_name`, `sensorPrecision`, `units`, `positions`, `isActive`, `showType`, `minValue`, `maxValue`, `rangeType`, `signType`, `conditionType`, `target`, `floating`)
 VALUES('3072','0','ORGANIC_POL','有机污染物','3','Hz','0','1','0','0','0','0','0',NULL,NULL,NULL);
INSERT INTO `m_sensorinfo` (`sensorPhysicalid`, `escape_sensor_id`, `en_name`, `cn_name`, `sensorPrecision`, `units`, `positions`, `isActive`, `showType`, `minValue`, `maxValue`, `rangeType`, `signType`, `conditionType`, `target`, `floating`)
 VALUES('3073','0','INORGANIC_POL','无机污染物','3','Hz','0','1','0','0','0','0','0',NULL,NULL,NULL);
INSERT INTO `m_sensorinfo` (`sensorPhysicalid`, `escape_sensor_id`, `en_name`, `cn_name`, `sensorPrecision`, `units`, `positions`, `isActive`, `showType`, `minValue`, `maxValue`, `rangeType`, `signType`, `conditionType`, `target`, `floating`)
 VALUES('3074','0','SULFUROUS_POL','含硫污染物','3','Hz','0','1','0','0','0','0','0',NULL,NULL,NULL);

INSERT INTO `m_formula_sensor` (`device_id`, `sensor_id`, `formula_id`, `min_x`, `max_x`, `x_range_type`, `min_y`, `max_y`, `y_range_type`, `sign_type`)
 VALUES('0','3072','1','0','0','0','0','0','0','0');
INSERT INTO `m_formula_sensor` (`device_id`, `sensor_id`, `formula_id`, `min_x`, `max_x`, `x_range_type`, `min_y`, `max_y`, `y_range_type`, `sign_type`)
 VALUES('0','3073','1','0','0','0','0','0','0','0');
INSERT INTO `m_formula_sensor` (`device_id`, `sensor_id`, `formula_id`, `min_x`, `max_x`, `x_range_type`, `min_y`, `max_y`, `y_range_type`, `sign_type`)
 VALUES('0','3074','1','0','0','0','0','0','0','0');

INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','3072','a','1');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','3072','b','0');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','3073','a','1');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','3073','b','0');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','3074','a','1');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','3074','b','0');



INSERT INTO `m_sensorinfo` (`sensorPhysicalid`, `escape_sensor_id`, `en_name`, `cn_name`, `sensorPrecision`, `units`, `positions`, `isActive`, `showType`, `minValue`, `maxValue`, `rangeType`, `signType`, `conditionType`, `target`, `floating`)
 VALUES('3075','0','ORGANIC_POL_DIF','有机污染物差值','2','Hz','0','1','0','0','0','0','0',NULL,NULL,NULL);
INSERT INTO `m_sensorinfo` (`sensorPhysicalid`, `escape_sensor_id`, `en_name`, `cn_name`, `sensorPrecision`, `units`, `positions`, `isActive`, `showType`, `minValue`, `maxValue`, `rangeType`, `signType`, `conditionType`, `target`, `floating`)
 VALUES('3076','0','INORGANIC_POL_DIF','无机污染物差值','2','Hz','0','1','0','0','0','0','0',NULL,NULL,NULL);
INSERT INTO `m_sensorinfo` (`sensorPhysicalid`, `escape_sensor_id`, `en_name`, `cn_name`, `sensorPrecision`, `units`, `positions`, `isActive`, `showType`, `minValue`, `maxValue`, `rangeType`, `signType`, `conditionType`, `target`, `floating`)
 VALUES('3077','0','SULFUROUS_POL_DIF','含硫污染物差值','2','Hz','0','1','0','0','0','0','0',NULL,NULL,NULL);
 
 INSERT INTO `m_formula_sensor` (`device_id`, `sensor_id`, `formula_id`, `min_x`, `max_x`, `x_range_type`, `min_y`, `max_y`, `y_range_type`, `sign_type`)
 VALUES('0','3075','1','0','0','0','0','0','0','0');
INSERT INTO `m_formula_sensor` (`device_id`, `sensor_id`, `formula_id`, `min_x`, `max_x`, `x_range_type`, `min_y`, `max_y`, `y_range_type`, `sign_type`)
 VALUES('0','3076','1','0','0','0','0','0','0','0');
INSERT INTO `m_formula_sensor` (`device_id`, `sensor_id`, `formula_id`, `min_x`, `max_x`, `x_range_type`, `min_y`, `max_y`, `y_range_type`, `sign_type`)
 VALUES('0','3077','1','0','0','0','0','0','0','0');

INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','3075','a','1');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','3075','b','0');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','3076','a','1');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','3076','b','0');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','3077','a','1');
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES('0','3077','b','0');