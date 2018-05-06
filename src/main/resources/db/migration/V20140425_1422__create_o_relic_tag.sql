-- author lijianfei
-- 创建文物标签表

CREATE TABLE o_relic_tag (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  relicId int(11) NOT NULL COMMENT '文物ID',
  name varchar(50) NOT NULL COMMENT '标签名称',
  PRIMARY KEY (id),
  KEY `FK_O_HISTORICAL_RELIC_ID` (relicId),
  CONSTRAINT `FK_O_HISTORICAL_RELIC_ID` FOREIGN KEY (relicId) REFERENCES o_historical_relic (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '文物标签表';