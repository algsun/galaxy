-- author xiedeng
-- description 修改so2_hs和voc_hs浮点数转移标识

UPDATE m_sensorinfo m SET m.`escape_sensor_id` = 85 WHERE m.`sensorPhysicalid`=2050;
UPDATE m_sensorinfo m SET m.`escape_sensor_id` = 83 WHERE m.`sensorPhysicalid`=2051;