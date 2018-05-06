--author: gaohui
--description: 修正 VOC 的范围限制

UPDATE `m_sensorinfo` SET rangeType = 3 WHERE sensorPhysicalId = 83;
