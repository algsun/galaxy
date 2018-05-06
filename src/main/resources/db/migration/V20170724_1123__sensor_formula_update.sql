--author li.jianfei
--description 修改VOC-高灵敏度公式类型,分段标定

UPDATE m_formula_sensor SET formula_id = 5 WHERE sensor_id IN (83, 2051);

DELETE FROM m_formula_param WHERE sensor_id IN (83, 2051) AND NAME IN ('c','d','e');
INSERT INTO m_formula_param(device_id, sensor_id, NAME, VALUE)
SELECT device_id, sensor_id, 'c' NAME, VALUE FROM m_formula_param WHERE sensor_id = 83 AND NAME = 'a' UNION
SELECT device_id, sensor_id, 'd' NAME, VALUE FROM m_formula_param WHERE sensor_id = 83 AND NAME = 'b' UNION
SELECT device_id, sensor_id, 'e' NAME, 32768 VALUE FROM m_formula_param WHERE sensor_id = 83 AND NAME = 'a' UNION
SELECT device_id, sensor_id, 'c' NAME, VALUE FROM m_formula_param WHERE sensor_id = 2051 AND NAME = 'a' UNION
SELECT device_id, sensor_id, 'd' NAME, VALUE FROM m_formula_param WHERE sensor_id = 2051 AND NAME = 'b' UNION
SELECT device_id, sensor_id, 'e' NAME, 32768 VALUE FROM m_formula_param WHERE sensor_id = 2051 AND NAME = 'a';

