-- author li.jianfei
-- 改变h_exhibition startTime 为 beginTime

-- 改变 startTime 为 beginTime
ALTER TABLE h_exhibition CHANGE COLUMN startTime beginTime DATETIME;