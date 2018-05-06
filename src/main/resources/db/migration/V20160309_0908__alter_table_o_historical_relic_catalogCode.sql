-- author : chen.yaoFei
-- description : alter table o_historical_relic catalogCode column

ALTER TABLE o_historical_relic  CHANGE  catalogCode catalogCode VARCHAR(20) NULL COMMENT "编目号";