--author xiedeng
--description 修改ph精度

UPDATE m_sensorinfo m SET m.`sensorPrecision` = 2 WHERE m.`sensorPhysicalid` = 57;