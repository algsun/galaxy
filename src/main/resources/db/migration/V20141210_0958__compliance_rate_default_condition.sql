-- 湿度 35-65±5
UPDATE m_sensorinfo SET conditionType = 1, target = 50, floating = 20 WHERE sensorPhysicalId=32;
-- 温度 18-22±2
UPDATE m_sensorinfo SET conditionType = 1, target = 20, floating = 4 WHERE sensorPhysicalId=33;
-- 二氧化碳 小于等于 1000
UPDATE m_sensorinfo set conditionType = 5, target = 1000 WHERE sensorPhysicalId=36;
-- 光照 小于等于 300
UPDATE m_sensorinfo SET conditionType = 5, target = 300 WHERE sensorPhysicalId=41;
-- 紫外 小于 20
UPDATE m_sensorinfo SET conditionType = 3, target = 20 WHERE sensorPhysicalId=42;
-- 甲醛 小于 80
UPDATE m_sensorinfo SET conditionType = 3, target = 80 WHERE sensorPhysicalId=91;
-- 臭氧 小于 5
UPDATE m_sensorinfo SET conditionType = 3, target = 5 WHERE sensorPhysicalId=2048;
-- 二氧化氮 小于 5
UPDATE m_sensorinfo SET conditionType = 3, target = 5 WHERE sensorPhysicalId=2049;
-- 二氧化硫 小于 4
UPDATE m_sensorinfo SET conditionType = 3, target = 4 WHERE sensorPhysicalId=83;
-- VOC 小于 300
UPDATE m_sensorinfo SET conditionType = 3, target = 300 WHERE sensorPhysicalId=85;
-- PM2.5 小于 75
UPDATE m_sensorinfo SET conditionType = 3, target = 75 WHERE sensorPhysicalId=2052;