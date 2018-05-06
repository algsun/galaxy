-- author gaohui
-- description 添加水速监测指标

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
  (90, 'PULSE', '水速', 2, 'm/s', '57', 1, 0, 0.06, 9.14, 3, 0);

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
VALUES ( '0', 90, 1, 0, 0, 0, 0.06, 9.14, 3, 0);

INSERT INTO `m_formula_param` (
  `device_id`,
  `sensor_id`,
  `name`,
  `value`
)
VALUES
  (
    '0',
    90,
    'a',
    '0.00006781684'
  ),(
    '0',
    90,
    'b',
    '0'
  );
