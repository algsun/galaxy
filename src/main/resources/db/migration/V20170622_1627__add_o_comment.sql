-- author bai.weixing
-- description 添加专家点评表comment

CREATE TABLE IF NOT EXISTS `o_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `organization` varchar(50) DEFAULT NULL COMMENT '组织单位',
  `expert` varchar(50) NOT NULL COMMENT '专家名称',
  `advice` varchar(300) NOT NULL COMMENT '验收意见',
  `date` datetime NOT NULL COMMENT '日期',
  `repairRecordId` int(11) NOT NULL COMMENT '修复记录id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专家点评表'