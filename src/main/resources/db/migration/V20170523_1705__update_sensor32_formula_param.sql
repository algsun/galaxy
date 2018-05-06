--author 李建飞
--description 修改湿度监测指标默认公式系数

UPDATE m_formula_param SET VALUE=-0.0000015955 WHERE device_id = 0 AND sensor_id = 32 AND NAME = 'a';
UPDATE m_formula_param SET VALUE=0.0367 WHERE device_id = 0 AND sensor_id = 32 AND NAME = 'b';
UPDATE m_formula_param SET VALUE=-2.0468 WHERE device_id = 0 AND sensor_id = 32 AND NAME = 'c';
