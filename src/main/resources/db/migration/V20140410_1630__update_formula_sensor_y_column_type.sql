-- author gaohui
-- description 更改 m_formula_sensor 中 min_y, max_y 类型为 float

ALTER TABLE `m_formula_sensor` CHANGE `min_y` `min_y` DOUBLE NOT NULL DEFAULT '0' COMMENT 'y 最小取值范围';
ALTER TABLE `m_formula_sensor` CHANGE `max_y` `max_y` DOUBLE NOT NULL DEFAULT '0' COMMENT 'y 最大取值范围';
