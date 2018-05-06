-- author lijianfei
-- description 统计数据迁移

-- 设备 --> 位置点  数据迁移
-- 在线设备均峰值数据表 
UPDATE m_avgdata t, m_location l SET t.nodeid = l.id WHERE t.nodeid = l.nodeid ;

-- 蒸发量小时统计
UPDATE m_tbl_evap_hour_acc t, m_location l SET t.nodeid = l.id WHERE t.nodeid = l.nodeid ;

-- LXH光照日统计表
UPDATE m_tbl_lxh_acc t, m_location l SET t.nodeid = l.id WHERE t.nodeid = l.nodeid ;

-- 日降水量
UPDATE m_tbl_rb_day_acc t, m_location l SET t.nodeid = l.id WHERE t.nodeid = l.nodeid ;

-- 小时降雨量表
UPDATE m_tbl_rb_hour_acc t, m_location l SET t.nodeid = l.id WHERE t.nodeid = l.nodeid ;

-- 风向玫瑰图数据表
UPDATE m_windrose t, m_location l SET t.nodeid = l.id WHERE t.nodeid = l.nodeid ;
