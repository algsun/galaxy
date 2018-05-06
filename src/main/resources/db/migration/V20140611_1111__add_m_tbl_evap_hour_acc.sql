-- author xiedeng
-- 添加表 m_tbl_evap_hour_acc

CREATE TABLE `m_tbl_evap_hour_acc` (
  `id` varchar(50) NOT NULL COMMENT '唯一标识uuid',
  `nodeid` varchar(20) NOT NULL COMMENT '产品入网唯一标识',
  `evap` double DEFAULT '0' COMMENT '蒸发量',
  `ms_datetime` datetime DEFAULT NULL COMMENT '记录时间',
  `isupdate` int(11) DEFAULT '0' COMMENT '是否修改',
  `dataVersion` bigint(20) NOT NULL DEFAULT '0' COMMENT '数据同步版本'
) ENGINE=InnoDB DEFAULT CHARSET=utf8

