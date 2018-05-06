-- author bai.weixing
-- description 添加站点

INSERT INTO t_site  (siteId, siteName, areaCode,lngBaiDu,latBaiDu)
SELECT '43010503', '湖南省博物馆临时库房', '430105',113.000399,28.217641 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM t_site WHERE siteId = '43010503');