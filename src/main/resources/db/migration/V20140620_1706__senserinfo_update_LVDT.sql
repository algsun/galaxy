--author wang.geng
--description 位移量监测指标更新最大值最小值为-50,+50
UPDATE `m_sensorinfo` m SET m.`minValue`=-50 , m.`maxValue`=50 WHERE m.`sensorPhysicalid`=79;
UPDATE `m_formula_sensor` n SET n.`min_y`=-50 , n.`max_y`=50 WHERE n.`sensor_id`=79
