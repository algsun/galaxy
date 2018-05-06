-- author lv.dongyu
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '13020301', '唐山博物馆', '130203', 118.198899, 39.637307 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '13020301');

-- author lv.dongyu
-- description更新站点

UPDATE t_site SET siteName='唐山博物馆',lngBaiDu='118.198899',latBaiDu='39.637307' WHERE siteId = '13020301';

