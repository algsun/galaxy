--author xiedeng
--description 修改温度的有符号标识

UPDATE m_sensorinfo m SET m.`signType` = 1 WHERE m.`sensorPhysicalid` = 33;
UPDATE m_formula_sensor m SET m.`sign_type` = 1 WHERE m.`sensor_id` = 33;