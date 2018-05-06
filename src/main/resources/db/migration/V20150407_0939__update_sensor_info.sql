--author 李建飞
--description 修改露点监测指标信息

UPDATE m_formula_sensor SET y_range_type = 0 WHERE sensor_id=43;
UPDATE m_sensorinfo SET en_name = 'DT/FT', cn_name = '露点/霜点' WHERE sensorPhysicalId=43;
