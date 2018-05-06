-- author gaohui
-- description 添加甲醛、二氧化氮、臭氧 监测指标

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
  (90, 'NO2-HS', '二氧化氮-高灵敏度', 0, 'ppb', '57', 1, 0, 0, 1000, 3, 0),
  (91, 'CH20-HS', '甲醛-高灵敏度', 0, 'ppb', '58', 1, 0, 0, 10000, 3, 1),
  (92, 'O3-HS', '臭氧-高灵敏度', 0, 'ppb', '59', 1, 0, 0, 500, 3, 0);

  -- 添加公式系数
INSERT INTO `m_defaultcoefficient` (
  `sensorPhysicalid`,
  `paramName`,
  `defaultValue`
)
VALUES
  (90, 'a', '0'),
  (90, 'b', '1'),
  (90, 'c', '0'),
  (91, 'a', '0'),
  (91, 'b', '1'),
  (91, 'c', '0'),
  (92, 'a', '0'),
  (92, 'b', '1'),
  (92, 'c', '0');
