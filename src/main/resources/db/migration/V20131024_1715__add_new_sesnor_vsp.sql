-- author gaohui
-- description 添加新的监测指标：土压力 kPa

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
  (86, 'VSP', '土压力', 2, 'kPa', '53', 1, 0, 0, 200, 3, 0);

  -- 添加公式系数
INSERT INTO `m_defaultcoefficient` (
  `sensorPhysicalid`,
  `paramName`,
  `defaultValue`
)
VALUES
  (86, 'a', '0'),
  (86, 'b', '0.01'),
  (86, 'c', '0');
