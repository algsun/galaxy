-- author gaohui
-- description 添加 GPS 相关监测指标

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
  (12287, 'LONGITUDE', '经度', 5, '°', '53', 1, 2, 0, 0, 0, 1),
  (12286, 'LATITUDE', '纬度', 5, '°', '54', 1, 2, 0, 0, 0, 1),
  (12285, 'ALTITUDE', '海拔', 1, 'm', '56', 1, 2, 0, 0, 0, 1),
  (12284, 'SPEED', '速率', 2, 'km/h', '57', 1, 2, 0, 0, 0, 0),
  (12283, 'DIRECTION', '航向', 1, '°', '58', 1, 2, 0, 0, 0, 0);

-- 添加公式系数
INSERT INTO `m_defaultcoefficient` (
  `sensorPhysicalid`,
  `paramName`,
  `defaultValue`
)
VALUES
  (12287, 'a', '0'),
  (12287, 'b', '1'),
  (12287, 'c', '0'),

  (12286, 'a', '0'),
  (12286, 'b', '1'),
  (12286, 'c', '0'),

  (12285, 'a', '0'),
  (12285, 'b', '1'),
  (12285, 'c', '0'),

  (12284, 'a', '0'),
  (12284, 'b', '1'),
  (12284, 'c', '0'),

  (12283, 'a', '0'),
  (12283, 'b', '1'),
  (12283, 'c', '0');