-- author : bai.weixing
-- description : 更新湿度原始值范围无限制

-- 更新数据
UPDATE `m_sensorinfo` SET rangeType = 0 WHERE sensorPhysicalId = 32;
UPDATE `m_formula_sensor` SET x_range_type = 0 ,min_x=0,max_x=0 WHERE sensor_id = 32

