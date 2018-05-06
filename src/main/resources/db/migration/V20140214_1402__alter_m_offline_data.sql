-- author xu.baoji
-- 离线数据 表添加 行号记录

ALTER TABLE `m_offline_data`  ADD COLUMN `rowNumber` INT(11) NOT NULL COMMENT "行号"
