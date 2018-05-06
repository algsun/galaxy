DELETE FROM m_sensorinfo WHERE sensorPhysicalid = 2074;
INSERT INTO m_sensorinfo (sensorPhysicalid, en_name, cn_name, sensorPrecision, units, showType, signType, `maxValue`, `minValue`, rangeType)
SELECT 2074, 'TMT', '温度', 1, '℃', 0, 1, 600, -40, 3 FROM DUAL;

DELETE FROM m_formula_sensor WHERE sensor_id = 2074;
INSERT INTO m_formula_sensor(sensor_id, formula_id, min_x, max_x, x_range_type, min_y, max_y, y_range_type, sign_type)
SELECT 2074, 1, -40, 600, 3, -40, 600, 3, 1 FROM dual;


DELETE FROM m_formula_param WHERE sensor_id = 2074;
INSERT INTO m_formula_param(sensor_id, name, value)
SELECT 2074, 'a', 1 FROM dual UNION
SELECT 2074, 'b', 0 FROM dual;
