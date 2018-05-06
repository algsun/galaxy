--author liuzhu
--description 添加上传状态

ALTER TABLE m_nodeinfo ADD COLUMN `uploadState` INT(11) NOT NULL DEFAULT 0 COMMENT '上传状态：0 未上传 1 已上传';
