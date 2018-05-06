-- author bai.weixing
-- description 修改字段名

ALTER TABLE m_nodeinfo
  CHANGE isHumdityDevice deviceType INT (1) COMMENT "设备类型：0-其他, 1-恒湿机, 2-空调"