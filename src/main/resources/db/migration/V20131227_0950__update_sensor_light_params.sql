-- author gaohui
-- description 修正光照公式系数

-- 删除之前的公式系数
DELETE FROM `m_defaultcoefficient` WHERE sensorPhysicalid =  41;

-- 添加公式系数
INSERT INTO `m_defaultcoefficient` (
  `sensorPhysicalid`,
  `paramName`,
  `defaultValue`
)
VALUES
  (41, 'a', '0'),
  (41, 'b', '0.06926'),
  (41, 'c', '-0.96964');
