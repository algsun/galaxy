-- author li.jianfei
-- description 修改PH值下限

UPDATE m_sensorinfo SET minValue = 0.1 WHERE sensorPhysicalId= 57;
UPDATE m_formula_sensor SET min_y = 0.1 WHERE sensor_id= 57;
