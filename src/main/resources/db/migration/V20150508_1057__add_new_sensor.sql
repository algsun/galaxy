DELETE FROM m_sensorinfo WHERE sensorPhysicalid BETWEEN 2060 AND 2070;
INSERT INTO m_sensorinfo (sensorPhysicalid, en_name, cn_name, sensorPrecision, units, showType, signType)
SELECT 2060, 'HUM', '湿度-气站', 4, '%', 0, 1 FROM dual UNION
SELECT 2061, 'TMT', '温度-气站', 5, '℃', 0, 1 FROM dual UNION
SELECT 2062, 'WDD', '风向-气站', 4, '°', 1, 1 FROM dual UNION
SELECT 2063, 'WDP', '风速-气站', 5, 'm/s', 0, 1 FROM dual UNION
SELECT 2064, 'PA', '压力-气站', 3, 'kPa', 0, 1 FROM dual UNION
SELECT 2065, 'SO2', '二氧化硫-气站', 4, 'mg/m³', 0, 1 FROM dual UNION
SELECT 2066, 'CO', '一氧化碳-气站', 4, 'mg/m³', 0, 1 FROM dual UNION
SELECT 2067, 'O3', '臭氧-气站', 3, 'mg/m³', 0, 1 FROM dual UNION
SELECT 2068, 'PM2.5', 'PM2.5-气站', 4, 'mg/m³', 0, 1 FROM dual UNION
SELECT 2069, 'PM10', 'PM10-气站', 4, 'mg/m³', 0, 1 FROM dual UNION
SELECT 2070, 'NOX', '氮氧化物-气站', 4, 'mg/m³', 0, 1 FROM dual;

DELETE FROM m_formula_sensor WHERE sensor_id BETWEEN 2060 AND 2070;
INSERT INTO m_formula_sensor(sensor_id, formula_id, sign_type)
SELECT 2060, 1, 1 FROM dual UNION
SELECT 2061, 1, 1 FROM dual UNION
SELECT 2062, 1, 1 FROM dual UNION
SELECT 2063, 1, 1 FROM dual UNION
SELECT 2064, 1, 1 FROM dual UNION
SELECT 2065, 1, 1 FROM dual UNION
SELECT 2066, 1, 1 FROM dual UNION
SELECT 2067, 1, 1 FROM dual UNION
SELECT 2068, 1, 1 FROM dual UNION
SELECT 2069, 1, 1 FROM dual UNION
SELECT 2070, 1, 1 FROM dual;


DELETE FROM m_formula_param WHERE sensor_id BETWEEN 2060 AND 2070;
INSERT INTO m_formula_param(sensor_id, name, value)
SELECT 2060, 'a', 1 FROM dual UNION
SELECT 2061, 'a', 1 FROM dual UNION
SELECT 2062, 'a', 1 FROM dual UNION
SELECT 2063, 'a', 1 FROM dual UNION
SELECT 2064, 'a', 1 FROM dual UNION
SELECT 2065, 'a', 1 FROM dual UNION
SELECT 2066, 'a', 1 FROM dual UNION
SELECT 2067, 'a', 1 FROM dual UNION
SELECT 2068, 'a', 1 FROM dual UNION
SELECT 2069, 'a', 1 FROM dual UNION
SELECT 2070, 'a', 1 FROM dual UNION
SELECT 2060, 'b', 0 FROM dual UNION
SELECT 2061, 'b', 0 FROM dual UNION
SELECT 2062, 'b', 0 FROM dual UNION
SELECT 2063, 'b', 0 FROM dual UNION
SELECT 2064, 'b', 0 FROM dual UNION
SELECT 2065, 'b', 0 FROM dual UNION
SELECT 2066, 'b', 0 FROM dual UNION
SELECT 2067, 'b', 0 FROM dual UNION
SELECT 2068, 'b', 0 FROM dual UNION
SELECT 2069, 'b', 0 FROM dual UNION
SELECT 2070, 'b', 0 FROM dual;
