-- author xiedeng
-- 添加动作模式字段

ALTER TABLE `m_control_module_switch_change`
  ADD COLUMN actionDriver INT NOT NULL COMMENT '动作模式，1 条件反射， 0 正常'