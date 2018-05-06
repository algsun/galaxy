--author lijianfei
--description 文物表添加 tagCode,并初始化数据

ALTER TABLE o_historical_relic ADD COLUMN tagCode VARCHAR(20) AFTER catalogCode;
UPDATE o_historical_relic r SET r.tagCode = LPAD(r.id, 6, 0);
