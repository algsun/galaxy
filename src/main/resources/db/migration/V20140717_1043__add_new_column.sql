-- author xiedeng
-- description m_sensorInfo 表添加列 escape_sensor_id
-- date 2014-7-14

-- m_sensorInfo 表添加列  escape_sensor_id
ALTER TABLE m_sensorinfo ADD COLUMN escape_sensor_id INT(11) NOT NULL DEFAULT '0' COMMENT '转义传感量标识' AFTER sensorPhysicalid;