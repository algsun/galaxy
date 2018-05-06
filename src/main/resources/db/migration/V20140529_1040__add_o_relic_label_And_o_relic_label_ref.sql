-- author xiedeng
-- 删除表  o_relic_tag
-- 添加表  o_relic_label  o_relic_label_ref

DROP TABLE IF EXISTS`o_relic_tag`;

CREATE TABLE `o_relic_label` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文物标签编号',
  `labelName` varchar(50) NOT NULL COMMENT '文物标签名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `labelName` (`labelName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `o_relic_label_ref` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `relicId` int(11) NOT NULL COMMENT '文物ID',
  `labelId` int(11) DEFAULT NULL COMMENT '标签编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `relicId` (`relicId`,`labelId`),
  KEY `FK_O_HISTORICAL_RELIC_ID` (`relicId`),
  KEY `FK_O_RELIC_LABEL_LABELID` (`labelId`),
  CONSTRAINT `FK_O_HISTORICAL_RELIC_ID` FOREIGN KEY (`relicId`) REFERENCES `o_historical_relic` (`id`),
  CONSTRAINT `FK_O_RELIC_LABEL_LABELID` FOREIGN KEY (`labelId`) REFERENCES `o_relic_label` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文物标签表';

