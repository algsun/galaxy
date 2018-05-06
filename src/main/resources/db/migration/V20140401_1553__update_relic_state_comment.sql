-- author gaohui
-- description 更新文物状态注释

ALTER TABLE `o_historical_relic` CHANGE `state` `state` INT(11) NOT NULL COMMENT '文物状态：0、在库; 3、出库申请中; 1、待出库; 2、出库';
