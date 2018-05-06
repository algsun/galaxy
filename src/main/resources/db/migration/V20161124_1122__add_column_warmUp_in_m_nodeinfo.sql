-- author:li.jianfei
-- description: 设备信息表添加预热时间

ALTER TABLE m_nodeinfo ADD COLUMN warmUp INT(11) DEFAULT 0
  COMMENT '设备预热时间，用于限定设备工作周期（设备工作周期必须大于预热时间）' AFTER siteId;
