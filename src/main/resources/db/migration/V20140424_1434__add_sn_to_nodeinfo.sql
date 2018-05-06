--author gaohui
--description 添加产品序列号

ALTER TABLE m_nodeinfo ADD COLUMN `sn` INT(11) NOT NULL DEFAULT 0 COMMENT '产品序列号';
