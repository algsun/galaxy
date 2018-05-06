-- author gaohui
-- description 批量更新监测指标参数

-- 光照
UPDATE `m_sensorinfo` SET signType = 1  WHERE sensorPhysicalid = 41;

-- 紫外
UPDATE `m_sensorinfo` SET signType = 1  WHERE sensorPhysicalid = 42;

-- VOC
UPDATE `m_sensorinfo` SET signType = 1  WHERE sensorPhysicalid = 46;

-- 风向
UPDATE `m_sensorinfo` SET  minValue = 0, `maxValue` = 360, rangeType = 3, signType = 1  WHERE sensorPhysicalid = 48;

-- 风速
UPDATE `m_sensorinfo` SET  minValue = 0, `maxValue` = 30, rangeType = 3, signType = 1  WHERE sensorPhysicalid = 49;

-- 水温
UPDATE `m_sensorinfo` SET  minValue = -60, `maxValue` = 100, rangeType = 3, signType = 1  WHERE sensorPhysicalid = 56;

-- PH值
UPDATE `m_sensorinfo` SET  minValue = 0, `maxValue` = 14, rangeType = 3, signType = 1  WHERE sensorPhysicalid = 57;

-- 溶氧
UPDATE `m_sensorinfo` SET  minValue = 0, `maxValue` = 20, rangeType = 3, signType = 1  WHERE sensorPhysicalid = 58;

-- 位移
UPDATE `m_sensorinfo` SET signType = 1  WHERE sensorPhysicalid = 79;

-- 蒸发量
UPDATE `m_sensorinfo` SET  minValue = -100, `maxValue` = 100, rangeType = 3, signType = 1  WHERE sensorPhysicalid = 80;

-- 液位
UPDATE `m_sensorinfo` SET  minValue = 0, `maxValue` = 10000, rangeType = 3, signType = 1  WHERE sensorPhysicalid = 82;

-- VOC-高灵敏度
UPDATE `m_sensorinfo` SET signType = 1  WHERE sensorPhysicalid = 83;
