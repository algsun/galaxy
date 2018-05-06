-- author : chen.yaoFei
-- description : o_historical_relic alter totalCode column

ALTER TABLE o_historical_relic CHANGE totalCode totalCode  VARCHAR(20) NULL  COMMENT '文物总登记号';