-- author lv.dongyu
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '23010301', '黑龙江省博物馆', '230103', 126.647534, 45.763371 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '23010301');

-- author lv.dongyu
-- description更新站点

UPDATE t_site SET siteName='黑龙江省博物馆',lngBaiDu='126.647534',latBaiDu='45.763371' WHERE siteId = '23010301';

