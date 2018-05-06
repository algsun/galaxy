--author liuzhu
--description 修改产品序列号类型

ALTER TABLE m_nodeinfo MODIFY sn VARCHAR(20) NOT NULL DEFAULT '0'  COMMENT '产品序列号';
