--修改X,Y,Z方向裂隙的范围
--王耕
UPDATE `m_formula_sensor` SET min_y = '-1' WHERE sensor_id IN (76,77,78);
UPDATE `m_sensorinfo` SET minValue = '-1' WHERE sensorPhysicalid IN (76,77,78);