--author gaohui
--description 更新 43 露点的取值范围

UPDATE `m_sensorinfo` SET minValue = 0, `maxValue` = 120, rangeType = 3 WHERE sensorPhysicalid = 43;
