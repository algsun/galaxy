-- author xiedeng
-- description 添加二氧化氮-高灵敏度、voc-高灵敏度、so2-高灵敏度 监测指标
-- date 2014-7-14

-- 添加二氧化氮监测指标
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
  (2049, 'NO2-HS', '二氧化氮-高灵敏度', 0, 'ppb', '0', 1, 0, 0, 1000, 3, 0);

INSERT INTO `m_formula_sensor`
            ( `device_id`,
             `sensor_id`,
             `formula_id`,
             `min_x`,
             `max_x`,
             `x_range_type`,
             `min_y`,
             `max_y`,
             `y_range_type`,
             `sign_type`)
VALUES ( '0', 2049, 1, 0, 0, 0, 0, 1000, 3, 0);

INSERT INTO `m_formula_param` (
  `device_id`,
  `sensor_id`,
  `name`,
  `value`
)
VALUES
  (
    '0',
    2049,
    'a',
    '1000'
  ),(
    '0',
    2049,
    'b',
    '0'
  );

-- 添加so2监测指标
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
  (2050, 'SO2-HS', '二氧化硫-高灵敏度', 0, 'ppb', '0', 1, 0, 0, 10000, 3, 0);

INSERT INTO `m_formula_sensor`
            ( `device_id`,
             `sensor_id`,
             `formula_id`,
             `min_x`,
             `max_x`,
             `x_range_type`,
             `min_y`,
             `max_y`,
             `y_range_type`,
             `sign_type`)
VALUES ( '0', 2050, 1, 0, 0, 0, 0, 10000, 3, 0);

INSERT INTO `m_formula_param` (
  `device_id`,
  `sensor_id`,
  `name`,
  `value`
)
VALUES
  (
    '0',
    2050,
    'a',
    '1000'
  ),(
    '0',
    2050,
    'b',
    '0'
  );

  -- 添加voc监测指标
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
  (2051, 'VOC-HS', 'VOC-高灵敏度', 0, 'ppb', '0', 1, 0, 0, 20000, 3, 0);

INSERT INTO `m_formula_sensor`
            ( `device_id`,
             `sensor_id`,
             `formula_id`,
             `min_x`,
             `max_x`,
             `x_range_type`,
             `min_y`,
             `max_y`,
             `y_range_type`,
             `sign_type`)
VALUES ( '0', 2051, 1, 0, 0, 0, 0, 20000, 3, 0);

INSERT INTO `m_formula_param` (
  `device_id`,
  `sensor_id`,
  `name`,
  `value`
)
VALUES
  (
    '0',
    2051,
    'a',
    '1000'
  ),(
    '0',
    2051,
    'b',
    '0'
  );