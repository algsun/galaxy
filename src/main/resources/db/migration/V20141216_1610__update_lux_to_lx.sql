--修改 lux 单位为 lx  水速 最小值为0
--谢登
UPDATE `m_sensorinfo` m SET m.`units` = 'lx' WHERE m.`sensorPhysicalid` = 41;
UPDATE `m_sensorinfo` m SET m.`minValue` = 0 WHERE m.`sensorPhysicalid` = 90;
UPDATE `m_formula_sensor` m SET m.`min_y` = 0 WHERE m.`sensor_id` = 90;


