-- author:chen.yaoFei
-- description: add column locationType from  m_location


ALTER TABLE m_location ADD COLUMN type INT(1) DEFAULT 0  NULL COMMENT "位置点类型：0:设备位置点;1:批次位置点;" AFTER createTime;