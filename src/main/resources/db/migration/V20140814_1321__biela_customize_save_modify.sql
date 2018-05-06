--wang.geng
--修改b_map_customize修改关联设备为关联位置点
ALTER TABLE b_map_customize CHANGE deviceId locationId VARCHAR(20) COMMENT '关联的位置点ID';