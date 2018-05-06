-- author lv.dongyu
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '64010501', '西夏陵', '640105', 106.002905, 38.436698 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '64010501');