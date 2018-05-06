-- author gaohui
-- description update 35 粉尘公式参数

-- 更新结果值范围
UPDATE `m_formula_sensor` SET y_range_type = 3, min_y = 0, max_y = 10 WHERE sensor_id = 35 AND device_id = 0;

-- 更新公式系数
DELETE FROM `m_formula_param` WHERE device_id = '0' AND sensor_id = 35;
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES
	('0', 35, 'a', '0.000036'),
	('0', 35, 'b', '0');


-- 删除 90 二氧化氮-高灵敏度, 91 甲醛-高灵敏度, 92 臭氧-高灵敏度
DELETE FROM `m_formula_sensor` WHERE device_id = '0' AND sensor_id IN(90, 91, 92);
DELETE FROM `m_formula_param` WHERE device_id = '0' AND sensor_id IN(90, 91, 92);
DELETE FROM `m_sensorinfo` WHERE sensorPhysicalid IN(90, 91, 92);


-- 添加 2048 臭氧传感量
-- 添加监测指标
INSERT INTO `m_sensorinfo` (
  `sensorPhysicalid`,
  `en_name`,
  `cn_name`,
  `sensorPrecision`,
  `units`,
  `positions`,
  `isActive`,
  `showType`,
  `minValue`,
  `maxValue`,
  `rangeType`,
  `signType`
)
VALUES
  (2048, 'O3-HS', '臭氧-高灵敏度', 0, 'ppb', '56', 1, 0, 0, 500, 3, 0);
/* 监测指标公式 */
INSERT INTO `m_formula_sensor` (`device_id`, `sensor_id`, `formula_id`, `min_x`, `max_x`, `x_range_type`, `min_y`, `max_y`, `y_range_type`, `sign_type`) VALUES
	('0', 2048, 1, 0, 0, 0, 0, 500, 3, 0);
/* 公式系数 */
INSERT INTO `m_formula_param` (`device_id`, `sensor_id`, `name`, `value`) VALUES
	('0', 2048, 'a', '1000'),
	('0', 2048, 'b', '0');
