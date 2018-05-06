-- author songtao
-- description 添加甲醛-高灵敏度监测指标

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
  (91, 'CH20-HS', '甲醛-高灵敏度', 0, 'ppb', '0', 1, 0, 0, 10000, 3, 1);

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
VALUES ( '0', 91, 1, 0, 0, 0, 0, 10000, 3, 1);

INSERT INTO `m_formula_param` (
  `device_id`,
  `sensor_id`,
  `name`,
  `value`
)
VALUES
  (
    '0',
    91,
    'a',
    '1'
  ),(
    '0',
    91,
    'b',
    '0'
  );
