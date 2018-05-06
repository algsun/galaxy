--liu.zhu
--修改dc_chart_condition字段deviceId为locaitonId
ALTER TABLE dc_chart_condition CHANGE deviceId locationId VARCHAR(20) COMMENT '关联的位置点ID';