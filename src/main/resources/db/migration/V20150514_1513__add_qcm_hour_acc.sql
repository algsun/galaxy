-- author liuzhu
-- description 添加qcm统计表
-- date 2015-5-7

CREATE TABLE `m_tbl_op_hour_acc` (
  `id` VARCHAR(50) NOT NULL COMMENT '唯一标识uuid',
  `locationId` VARCHAR(20) NOT NULL COMMENT '位置点id',
  `op` DOUBLE DEFAULT '0' COMMENT '有机污染物',
  `ms_datetime` DATETIME DEFAULT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='小时有机污染物表';

CREATE TABLE `m_tbl_ip_hour_acc` (
  `id` VARCHAR(50) NOT NULL COMMENT '唯一标识uuid',
  `locationId` VARCHAR(20) NOT NULL COMMENT '位置点id',
  `ip` DOUBLE DEFAULT '0' COMMENT '无机污染物',
  `ms_datetime` DATETIME DEFAULT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='小时无机污染物表';

CREATE TABLE `m_tbl_sp_hour_acc` (
  `id` VARCHAR(50) NOT NULL COMMENT '唯一标识uuid',
  `locationId` VARCHAR(20) NOT NULL COMMENT '位置点id',
  `sp` DOUBLE DEFAULT '0' COMMENT '含硫污染物',
  `ms_datetime` DATETIME DEFAULT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='小时含硫污染物表';