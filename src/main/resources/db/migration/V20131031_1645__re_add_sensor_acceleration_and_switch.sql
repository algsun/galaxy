-- author gaohui
-- description 删除之前的 XYZ 三个方向的加速度监测指标；添加加速度、震动、开关量监测指标。

-- 删除 XYZ 加速度
DELETE FROM `m_defaultcoefficient` WHERE sensorPhysicalid IN (87, 88, 89);

DELETE FROM `m_sensorinfo` WHERE sensorPhysicalid IN (87, 88, 89);

-- 添加监测指标
INSERT INTO `m_sensorinfo` (
  `sensorPhysicalid`,
  `en_name`,
  `cn_name`,
  `sensorPrecision`,
  `units`,
  `positions`,
  `isActive`,
  `showType`,
  `minValue`,
  `maxValue`,
  `rangeType`,
  `signType`
)
VALUES
  (87, 'ACCL', '加速度', 2, 'g', '54', 1, 0, -10, 10, 3, 1),
  (88, 'SHAKE', '震动', 2, 'g', '55', 1, 0, -10, 10, 3, 1),
  (89, 'SWH', '开关量', 0, ' ', '56', 1, 0, 0, 1, 3, 0);

-- 添加公式系数
INSERT INTO `m_defaultcoefficient` (
  `sensorPhysicalid`,
  `paramName`,
  `defaultValue`
)
VALUES
  (87, 'a', '0'),
  (87, 'b', '0.000305203723485426'),
  (87, 'c', '0'),
  (88, 'a', '0'),
  (88, 'b', '0.000305203723485426'),
  (88, 'c', '0'),
  (89, 'a', '0'),
  (89, 'b', '1'),
  (89, 'c', '0');
