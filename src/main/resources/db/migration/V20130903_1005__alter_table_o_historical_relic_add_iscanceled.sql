--author xu.baoji
--description 文物基本信息表添加 是否注销字段
ALTER TABLE o_historical_relic  ADD is_canceled INT(1)NOT NULL DEFAULT '0' COMMENT '是否注销：0 未注销，1 注销'
