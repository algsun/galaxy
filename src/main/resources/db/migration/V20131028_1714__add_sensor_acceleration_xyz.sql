--author gaohui
--description  添加 XYZ 方向加速度监测指标

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
  (87, 'ACCL-X', 'X方向加速度', 2, 'g', '54', 1, 0, -10, 10, 3, 1),
  (88, 'ACCL-Y', 'Y方向加速度', 2, 'g', '55', 1, 0, -10, 10, 3, 1),
  (89, 'ACCL-Z', 'Z方向加速度', 2, 'g', '56', 1, 0, -10, 10, 3, 1) ;

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
  (89, 'b', '0.000305203723485426'),
  (89, 'c', '0') ;
