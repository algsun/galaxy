-- author lv.dongyu
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '61011608', '长安区文化中心', '610116', 108.886274, 34.162573 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '61011608');