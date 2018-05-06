-- author bai.weixing
-- description 添加文物健康评测表comment

CREATE TABLE IF NOT EXISTS `o_health_evaluation` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `relicId` int(32) NOT NULL COMMENT '文物id',
  `evaluator` varchar(20) NOT NULL COMMENT '评测人员',
  `sample_number` varchar(50) NOT NULL COMMENT '样品编号',
  `sample_desc` varchar(50) DEFAULT NULL COMMENT '样品描述',
  `conclusion` smallint(1) NOT NULL COMMENT '综合评测结论',
  `suggestion` smallint(1) NOT NULL COMMENT '保护建议',
  `evaluation_date` datetime NOT NULL COMMENT '评测日期',
  `statusQuoId` int(11) NOT NULL COMMENT '文物现状记录信息id',
  PRIMARY KEY (`id`),
  KEY `relicId` (`relicId`),
  CONSTRAINT `o_health_evaluation_ibfk_1` FOREIGN KEY (`relicId`) REFERENCES `o_historical_relic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8

