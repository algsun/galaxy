-- author gaohui
-- description  补 92 臭氧-高灵敏度 公式及系数 ;修正 32 湿度的公式 x 范围

/* 公式系数 */
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES
	('0', 92, 'a', '1'),
	('0', 92, 'b', '0');

/* 监测指标公式 */
INSERT INTO `m_formula_sensor` (`device_id`, `sensor_id`, `formula_id`, `min_x`, `max_x`, `x_range_type`, `min_y`, `max_y`, `y_range_type`, `sign_type`) VALUES
	('0', 92, 1, 0, 500, 3, 0, 0, 0, 0);

UPDATE `m_formula_sensor` SET x_range_type = 3, min_x = 100, max_x = 3338 WHERE sensor_id = 32 AND device_id = 0;
