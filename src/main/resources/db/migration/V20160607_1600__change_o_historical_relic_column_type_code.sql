-- author : li.jianfei
-- date   : 2016-06-07

ALTER TABLE `o_historical_relic`
  CHANGE `typeCode` `typeCode` VARCHAR(20) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '分类号',
  CHANGE `state` `state` INT(11) DEFAULT 0  NOT NULL  COMMENT '文物状态：0、在库; 3、出库申请中; 1、待出库; 2、出库';
