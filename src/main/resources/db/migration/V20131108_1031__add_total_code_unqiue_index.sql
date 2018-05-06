-- author gaohui
-- description o_historical_relic (total_code, siteId) 添加联合唯一约束

ALTER TABLE `o_historical_relic` ADD UNIQUE INDEX(totalCode, siteId);
