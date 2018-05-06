-- author:li.jianfei
-- description: 设备实时状态表添加预热时间

ALTER TABLE m_nodeinfomemory ADD COLUMN warmUp INT(11) DEFAULT 0
  COMMENT '设备预热时间，用于限定设备工作周期（设备工作周期必须大于预热时间）' AFTER emptyStamp;

ALTER TABLE m_nodeinfo DROP COLUMN warmUp;
