-- author gaohui
-- description 添加 "二氧化硫-高灵敏度" 监测指标与公式系数

-- 添加监测指标
INSERT INTO `m_sensorinfo` (
  `id`,
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
  (
    53,
    85,
    'SO2_HS',
    '二氧化硫-高灵敏度',
    0,
    'ppb',
    '52',
    1,
    0,
    0,
    10000,
    3,
    1
  );

-- 添加公式系数
INSERT INTO `m_defaultcoefficient` (
  `sensorPhysicalid`,
  `paramName`,
  `defaultValue`
)
VALUES
  (85, 'a', '0'),
  (85, 'b', '0.495241'),
  (85, 'c', '-28.04') ;