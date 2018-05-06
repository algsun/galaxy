--author 李建飞
--description 修改2048，2049，2050，2051监测指标公式参数

UPDATE m_formula_param SET VALUE=1 WHERE device_id = 0 AND sensor_id = 2048 AND NAME = 'a';
UPDATE m_formula_param SET VALUE=1 WHERE device_id = 0 AND sensor_id = 2049 AND NAME = 'a';
UPDATE m_formula_param SET VALUE=1 WHERE device_id = 0 AND sensor_id = 2050 AND NAME = 'a';
UPDATE m_formula_param SET VALUE=1 WHERE device_id = 0 AND sensor_id = 2051 AND NAME = 'a';