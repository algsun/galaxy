--author gaohui
--description 调整土压力传感参数

UPDATE `m_sensorinfo` SET minValue = -200, `maxValue` = 200, signType = 1 WHERE sensorPhysicalid = 86;
