--wang.geng
--修改dc_program字段deviceId为locaitonId
ALTER TABLE dc_program CHANGE deviceId locationId VARCHAR(20) COMMENT '关联的位置点ID';