--author 谢登
--description 超出范围显示限定值

ALTER TABLE `m_formula_sensor` CHANGE y_range_type
`y_range_type` INT(1) NOT NULL DEFAULT '0' COMMENT '无范围限制 0; 只有最小值限制 1; 只有最大值限制 2; 两个都有 3;超出范围显示限定值 4;';
