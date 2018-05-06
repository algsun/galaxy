--author li.jianfei
--description 修改水温监测指标Y值限制方法

UPDATE m_formula_sensor SET y_range_type=0 WHERE sensor_id=56;