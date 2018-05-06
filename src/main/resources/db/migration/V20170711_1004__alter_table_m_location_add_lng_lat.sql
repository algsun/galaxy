-- 给m_location表添加经度和维度两个字段

ALTER TABLE m_location ADD lng DECIMAL (10,7) COMMENT "经度";
ALTER TABLE m_location ADD lat DECIMAL (9,7) COMMENT "纬度";

