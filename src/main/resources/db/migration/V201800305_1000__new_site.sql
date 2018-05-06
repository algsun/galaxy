-- author lv.dongyu
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '23010304', '黑龙江省民族博物馆', '152921', 126.680728, 45.778111 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '23010304');

-- author lv.dongyu
-- description更新站点

UPDATE t_site SET siteName='黑龙江省民族博物馆',lngBaiDu='126.680728',latBaiDu='45.778111' WHERE siteId = '23010304';

