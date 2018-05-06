-- author 李建飞
-- description 添加标定模式字段
-- date 2015-06-05

ALTER TABLE m_nodeinfomemory ADD demarcate INT DEFAULT 0 NOT NULL COMMENT '0-费标定模式；1-标定模式';
